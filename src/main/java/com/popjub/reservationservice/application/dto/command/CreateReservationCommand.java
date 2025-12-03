package com.popjub.reservationservice.application.dto.command;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

public record CreateReservationCommand(
	Long userId,
	UUID timeslotId,
	Integer friendCnt
) {
	public Reservation toEntity(SearchTimeslotResponse searchTimeslotResponse, String qrCode) {
		return Reservation.of(
			this.userId,
			searchTimeslotResponse.storeId(),
			this.timeslotId,
			this.friendCnt,
			searchTimeslotResponse.date(),
			qrCode
		);
	}
}