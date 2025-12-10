package com.popjub.reservationservice.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.CreateNoShowResult;

public record CreateNoShowResponse(
	UUID noShowId,
	Long userId,
	UUID reservationId,
	LocalDateTime createdAt
) {
	public static CreateNoShowResponse from(CreateNoShowResult result) {
		return new CreateNoShowResponse(
			result.noShowId(),
			result.userId(),
			result.reservationId(),
			result.createdAt()
		);
	}
}
