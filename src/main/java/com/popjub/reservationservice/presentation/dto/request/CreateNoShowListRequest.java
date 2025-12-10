package com.popjub.reservationservice.presentation.dto.request;

import java.util.List;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.command.CreateNoShowListCommand;

public record CreateNoShowListRequest(
	List<NoShowList> noShowList
) {
	public record NoShowList(
		Long userId,
		UUID reservationId
	) {
	}

	public CreateNoShowListCommand toCommand() {
		List<CreateNoShowListCommand.NoShowList> list = noShowList.stream()
			.map(lists -> new CreateNoShowListCommand.NoShowList(
				lists.userId,
				lists.reservationId
			)).toList();
		return new CreateNoShowListCommand(list);
	}
}
