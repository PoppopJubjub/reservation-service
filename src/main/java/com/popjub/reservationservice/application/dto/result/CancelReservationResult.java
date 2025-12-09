package com.popjub.reservationservice.application.dto.result;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record CancelReservationResult(
	UUID reservationId,
	ReservationStatus status
) {
	public static CancelReservationResult from(Reservation reservation) {
		return new CancelReservationResult(
			reservation.getReservationId(),
			reservation.getStatus()
		);
	}
}
