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
import com.popjub.reservationservice.application.port.RemainingPort;
import com.popjub.reservationservice.application.port.StoreServicePort;
import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;
import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.application.valid.ReservationValidator;
import com.popjub.reservationservice.application.valid.TimeslotValidator;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.domain.vo.RemainingCapacity;
import com.popjub.reservationservice.global.exception.ReservationCustomException;
import com.popjub.reservationservice.global.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final StoreServicePort storeServicePort;
	private final QrCodeService qrCodeService;
	private final RemainingPort remainingPort;
	private final ReservationValidator reservationValidator;
	private final TimeslotValidator timeslotValidator;

	/**
	 * 알림서비스 kafka 구현시 사용예정
	 */
	// private final ReservationEventPort eventPort;
	@Transactional
	public CreateReservationResult createReservation(CreateReservationCommand command) {
		TimeslotResult timeslotResult = storeServicePort.getTimeslot(command.timeslotId());

		timeslotValidator.validateTimeslot(timeslotResult);

		reservationValidator.validate(command, timeslotResult.storeId(), timeslotResult.reservationDate());

		RemainingCapacity remainingCapacity = remainingPort.decrease(command.timeslotId(), command.friendCnt() + 1);

		if (remainingCapacity.isZero()) {
			storeServicePort.updateTimeslotStatus(command.timeslotId(), TimeSlotStatus.FULL);
		}

		Reservation reservation = command.toEntity(timeslotResult, generatedQrcode());
		reservationRepository.save(reservation);

		return CreateReservationResult.from(reservation, timeslotResult.storeName(), timeslotResult.reservationTime());
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

		RemainingCapacity remainingCapacity = remainingPort.increase(reservation.getTimeslotId(),
			reservation.getFriendCnt() + 1);

		if (remainingCapacity.isNotZero()) {
			storeServicePort.updateTimeslotStatus(reservation.getTimeslotId(), TimeSlotStatus.AVAILABLE);
		}
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

	public ReservationStatus validReservation(UUID reservationId, Long userId, UUID storeId) {
		Reservation reservation = reservationRepository.findByReservationIdAndUserIdAndStoreId(reservationId, userId,
				storeId)
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.INVALID_RESERVATION_ACCESS));
		return reservation.getStatus();
	}
}
