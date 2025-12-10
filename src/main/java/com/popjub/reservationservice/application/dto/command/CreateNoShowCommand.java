package com.popjub.reservationservice.application.dto.command;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.NoShow;

public record CreateNoShowCommand(
	Long userId,
	UUID reservationId
) {
	public NoShow toEntity() {
		return NoShow.of(
			userId,
			reservationId
		);
	}
}
