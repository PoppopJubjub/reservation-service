package com.popjub.reservationservice.application.dto.result;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record CheckInResult(
	UUID reservationId,
	ReservationStatus status
) {
	public static CheckInResult from(Reservation reservation) {
		return new CheckInResult(
			reservation.getReservationId(),
			reservation.getStatus()
		);
	}
}
