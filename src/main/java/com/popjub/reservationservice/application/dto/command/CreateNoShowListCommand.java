package com.popjub.reservationservice.application.dto.command;

import java.util.List;
import java.util.UUID;

public record CreateNoShowListCommand(
	List<NoShowList> noShowList
) {
	public record NoShowList(
		Long userId,
		UUID reservationId
	) {
	}
}
