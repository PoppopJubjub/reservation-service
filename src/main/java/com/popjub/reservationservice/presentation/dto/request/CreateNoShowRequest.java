package com.popjub.reservationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.reservationservice.application.dto.command.CreateNoShowCommand;

public record CreateNoShowRequest(
	Long userId,
	UUID reservationId
) {
	public CreateNoShowCommand toCommand() {
		return new CreateNoShowCommand(
			this.userId,
			this.reservationId
		);
	}
}
