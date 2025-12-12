package com.popjub.reservationservice.application.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.result.CheckInResult;
import com.popjub.reservationservice.application.port.NotificationPort;
import com.popjub.reservationservice.application.port.StoreServicePort;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckInService {

	private final ReservationRepository reservationRepository;
	private final StoreServicePort storeServicePort;
	private final NotificationPort notificationPort;

	@Transactional
	public CheckInResult checkIn(String qrCode, Long userId) {
		Reservation reservation = reservationRepository.findByQrCode(qrCode)
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.INVALID_QR_CODE));

		if (reservation.isNotCompleted()) {
			throw new ReservationCustomException(ReservationErrorCode.CHECKIN_NOT_AVAILABLE);
		}

		if (reservation.validReservationDate(LocalDate.now())) {
			throw new ReservationCustomException(ReservationErrorCode.INVALID_CHECK_IN_DATE);
		}

		boolean isValid = storeServicePort.validateCheckIn(
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			userId
		);

		if (!isValid) {
			throw new ReservationCustomException(ReservationErrorCode.CHECKIN_VALIDATION_FAILED);
		}

		reservation.checkInReservation();
		reservationRepository.save(reservation);
		notificationPort.sendCheckInNotification(reservation);
		return CheckInResult.from(reservation);
	}
}
