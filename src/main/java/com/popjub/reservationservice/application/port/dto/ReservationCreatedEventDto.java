package com.popjub.reservationservice.application.port.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public record ReservationCreatedEventDto(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime reservationTime,
	Integer friendCnt
) {
	public static ReservationCreatedEventDto from(Reservation reservation, TimeslotResult timeslotResult) {
		return new ReservationCreatedEventDto(
			reservation.getReservationId(),
			reservation.getUserId(),
			timeslotResult.storeName(),
			reservation.getReservationDate(),
			timeslotResult.reservationTime(),
			reservation.getFriendCnt()
		);
	}
}
