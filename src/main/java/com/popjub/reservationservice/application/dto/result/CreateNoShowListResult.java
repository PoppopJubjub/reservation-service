package com.popjub.reservationservice.application.dto.result;

import java.util.List;

public record CreateNoShowListResult(
	List<CreateNoShowResult> list
) {
	public static CreateNoShowListResult of(List<CreateNoShowResult> list) {
		return new CreateNoShowListResult(list);
	}
}
