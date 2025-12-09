package com.popjub.reservationservice.application.dto.command;

import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.domain.entity.Reservation;

public record CreateReservationCommand(
	Long userId,
	UUID timeslotId,
	Integer friendCnt
) {
	public Reservation toEntity(TimeslotResult timeslotResult, String qrCode) {
		return Reservation.of(
			this.userId,
			timeslotResult.storeId(),
			this.timeslotId,
			this.friendCnt,
			timeslotResult.date(),
			qrCode
		);
	}
}