package com.popjub.reservationservice.application.dto.result;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public record CreateReservationResult(
	UUID reservationId
) {
	public static CreateReservationResult from(Reservation reservation) {
		return new CreateReservationResult(
			reservation.getReservationId()
		);
	}
}
