package com.popjub.reservationservice.presentation.dto.response;

import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.CreateReservationResult;

public record CreateReservationResponse(
	UUID reservationId
) {

	public static CreateReservationResponse from(CreateReservationResult result) {
		return new CreateReservationResponse(
			result.reservationId()
		);
	}
}
