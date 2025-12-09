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
import com.popjub.reservationservice.application.port.ReservationEventPort;
import com.popjub.reservationservice.application.port.StoreServicePort;
import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final StoreServicePort storeServicePort;
	private final QrCodeService qrCodeService;
	private final ReservationEventPort eventPort;

	@Transactional
	public CreateReservationResult createReservation(CreateReservationCommand command) {

		TimeslotResult timeslotResult = storeServicePort.getTimeslot(command.timeslotId());

		if (!timeslotResult.status().equals("AVAILABLE")) {
			throw new ReservationCustomException(ReservationErrorCode.TIMESLOT_NOT_AVAILABLE);
		}

		if (reservationRepository.existsByUserIdAndStoreIdAndReservationDate(
			command.userId(),
			timeslotResult.storeId(),
			timeslotResult.date())) {
			throw new ReservationCustomException(ReservationErrorCode.DUPLICATE_RESERVATION);
		}

		Reservation reservation = command.toEntity(timeslotResult, generatedQrcode());
		reservationRepository.save(reservation);

		eventPort.publishReservationCreated(
			reservation.getReservationId(),
			reservation.getUserId(),
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			reservation.getReservationDate(),
			reservation.getFriendCnt(),
			reservation.getQrCode()
		);

		return CreateReservationResult.from(reservation);
	}

	public SearchReservationDetailResult searchReservationDetail(UUID reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.NOT_FOUNT_RESERVATION));

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
