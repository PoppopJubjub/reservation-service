package com.popjub.reservationservice.presentation.dto.response;

import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.CancelReservationResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record CancelReservationResponse(
	UUID reservationId,
	ReservationStatus status
) {
	public static CancelReservationResponse from(CancelReservationResult result) {
		return new CancelReservationResponse(
			result.reservationId(),
			result.status()
		);
	}
}
