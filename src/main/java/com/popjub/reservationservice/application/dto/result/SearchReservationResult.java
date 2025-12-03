package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record SearchReservationResult(
	UUID reservationId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static SearchReservationResult from(Reservation reservation) {
		return new SearchReservationResult(
			reservation.getReservationId(),
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			reservation.getReservationDate(),
			reservation.getFriendCnt(),
			reservation.getStatus(),
			reservation.getCreatedAt()
		);
	}
}
