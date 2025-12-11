package com.popjub.reservationservice.presentation.dto.response;

import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.CheckInResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record CheckInResponse(
	UUID reservationId,
	ReservationStatus status
) {
	public static CheckInResponse from(CheckInResult result) {
		return new CheckInResponse(
			result.reservationId(),
			result.status()
		);
	}
}
