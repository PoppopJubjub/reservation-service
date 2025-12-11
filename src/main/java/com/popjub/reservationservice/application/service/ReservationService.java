package com.popjub.reservationservice.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CancelReservationResult;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchStoreReservationResult;
import com.popjub.reservationservice.application.dto.result.searchStoreReservationByFilterResult;
import com.popjub.reservationservice.application.port.NotificationPort;
import com.popjub.reservationservice.application.port.StoreServicePort;
import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;
import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;
import com.popjub.reservationservice.infrastructure.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final StoreServicePort storeServicePort;
	private final QrCodeService qrCodeService;
	private final RedisUtil redisUtil;
	private final NotificationPort notificationPort;
	private final NoShowService noShowService;

	/**
	 * 알림서비스 kafka 구현시 사용예정
	 */
	// private final ReservationEventPort eventPort;
	@Transactional
	public CreateReservationResult createReservation(CreateReservationCommand command) {

		if (noShowService.isRestricted(command.userId())) {
			throw new ReservationCustomException(ReservationErrorCode.NO_SHOW_RESTRICTED);
		}

		TimeslotResult timeslotResult = storeServicePort.getTimeslot(command.timeslotId());
		// "AVAILABLE" -> TimeSlotStatus.AVAILABLE
		TimeSlotStatus status = timeslotResult.status();

		// 필드를 가지고 있는 객채에 메세지를 보내서 결과를 처리해라
		// 이 방법을 수행하기 위해 Getter를 이용해서 필드를 꺼내지 말아라
		if (status.isNotAvailable()) {
			throw new ReservationCustomException(ReservationErrorCode.TIMESLOT_NOT_AVAILABLE);
		}

		try {
			redisUtil.decreaseRemaining(command.timeslotId());
		} catch (ReservationCustomException e) {
			throw e;
		}

		if (reservationRepository.existsByUserIdAndStoreIdAndReservationDate(
			command.userId(),
			timeslotResult.storeId(),
			timeslotResult.reservationDate())) {

			redisUtil.increaseRemaining(command.timeslotId());
			throw new ReservationCustomException(ReservationErrorCode.DUPLICATE_RESERVATION);
		}

		Reservation reservation = command.toEntity(timeslotResult, generatedQrcode());
		reservationRepository.save(reservation);

		notificationPort.sendReservationCreateNotification(
			reservation,
			timeslotResult.storeName(),
			timeslotResult.reservationTime()
		);
		// ReservationCreatedEventDto eventDto = ReservationCreatedEventDto.from(reservation, timeslotResult);
		// eventPort.publishReservationCreated(eventDto);

		return CreateReservationResult.from(reservation);
	}

	public SearchReservationDetailResult searchReservationDetail(UUID reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.NOT_FOUND_RESERVATION));

		String qrCodeImage = qrCodeService.generatedQrCodeImage(reservation.getQrCode());
		return SearchReservationDetailResult.from(reservation, qrCodeImage);
	}

	@Transactional
	public CancelReservationResult cancelReservation(UUID reservationId, Long userId) {
		Reservation reservation = reservationRepository.findByUserIdAndReservationIdAndStatus(userId, reservationId,
				ReservationStatus.COMPLETE)
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.CANNOT_CANCEL_RESERVATION));
		reservation.cancelReservation();
		reservationRepository.save(reservation);

		redisUtil.increaseRemaining(reservation.getTimeslotId());
		return CancelReservationResult.from(reservation);
	}

	public Page<SearchReservationResult> searchReservation(Long userId, Pageable pageable) {
		Page<Reservation> reservationPage = reservationRepository.findAllByUserId(userId, pageable);
		return reservationPage.map(SearchReservationResult::from);
	}

	public Page<SearchStoreReservationResult> searchStoreReservation(UUID storeId, Pageable pageable) {
		Page<Reservation> reservationPage = reservationRepository.findAllByStoreId(storeId, pageable);
		return reservationPage.map(SearchStoreReservationResult::from);
	}

	public Page<searchStoreReservationByFilterResult> searchStoreReservationByFilter(UUID storeId,
		ReservationStatus status, LocalDate reservationDate, Pageable pageable) {
		Page<Reservation> reservationPage = reservationRepository.findAllByStoreIdAndStatusAndReservationDate(storeId,
			status, reservationDate, pageable);
		return reservationPage.map(searchStoreReservationByFilterResult::from);
	}

	private String generatedQrcode() {
		return UUID.randomUUID().toString();
	}
}
