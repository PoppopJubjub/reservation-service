package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.NoShow;

public record CreateNoShowResult(
	UUID noShowId,
	Long userId,
	UUID reservationId,
	LocalDateTime createdAt
) {
	public static CreateNoShowResult from(NoShow noShow) {
		return new CreateNoShowResult(
			noShow.getNoShowId(),
			noShow.getUserId(),
			noShow.getReservationId(),
			noShow.getCreatedAt()
		);
	}
}
