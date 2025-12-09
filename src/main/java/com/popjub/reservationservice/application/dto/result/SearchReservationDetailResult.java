package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record SearchReservationDetailResult(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	String qrCode,
	String qrCodeImage,
	ReservationStatus status
) {
	public static SearchReservationDetailResult from(Reservation reservation, String qrCodeImage) {
		return new SearchReservationDetailResult(
			reservation.getReservationId(),
			reservation.getUserId(),
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			reservation.getReservationDate(),
			reservation.getFriendCnt(),
			reservation.getQrCode(),
			qrCodeImage,
			reservation.getStatus()
		);
	}
}
