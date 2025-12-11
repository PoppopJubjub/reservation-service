package com.popjub.reservationservice.presentation.dto.response;

import java.util.Map;
import java.util.UUID;

public record GetRemainingResponse(
	Map<UUID, Integer> remaining
) {
	public static GetRemainingResponse from(Map<UUID, Integer> remaining) {
		return new GetRemainingResponse(remaining);
	}
}
