package com.popjub.reservationservice.application.port.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public record ReservationCreatedEventDto(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	String qrCode
) {
	public static ReservationCreatedEventDto from(Reservation reservation) {
		return new ReservationCreatedEventDto(
			reservation.getReservationId(),
			reservation.getUserId(),
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			reservation.getReservationDate(),
			reservation.getFriendCnt(),
			reservation.getQrCode()
		);
	}
}
