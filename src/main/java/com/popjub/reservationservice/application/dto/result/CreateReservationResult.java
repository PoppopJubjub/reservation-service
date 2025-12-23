package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public record CreateReservationResult(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime reservationTime
) {
	public static CreateReservationResult from(Reservation reservation, String storeName, LocalTime reservationTime) {
		return new CreateReservationResult(
			reservation.getReservationId(),
			reservation.getUserId(),
			storeName,
			reservation.getReservationDate(),
			reservationTime
		);
	}
}
