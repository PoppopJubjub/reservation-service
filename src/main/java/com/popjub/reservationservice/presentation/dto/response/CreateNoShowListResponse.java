package com.popjub.reservationservice.presentation.dto.response;

import java.util.List;

import com.popjub.reservationservice.application.dto.result.CreateNoShowListResult;

public record CreateNoShowListResponse(
	List<CreateNoShowResponse> list
) {
	public static CreateNoShowListResponse from(CreateNoShowListResult result) {
		List<CreateNoShowResponse> responseList = result.list().stream()
			.map(CreateNoShowResponse::from)
			.toList();
		return new CreateNoShowListResponse(responseList);
	}
}
