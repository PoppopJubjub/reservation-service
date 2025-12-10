package com.popjub.reservationservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.reservationservice.application.dto.command.CreateNoShowCommand;
import com.popjub.reservationservice.application.dto.command.CreateNoShowListCommand;
import com.popjub.reservationservice.application.dto.result.CreateNoShowListResult;
import com.popjub.reservationservice.application.dto.result.CreateNoShowResult;
import com.popjub.reservationservice.application.service.NoShowService;
import com.popjub.reservationservice.presentation.dto.request.CreateNoShowListRequest;
import com.popjub.reservationservice.presentation.dto.request.CreateNoShowRequest;
import com.popjub.reservationservice.presentation.dto.response.CreateNoShowListResponse;
import com.popjub.reservationservice.presentation.dto.response.CreateNoShowResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noShow")
public class NoShowController {

	private final NoShowService noShowService;

	/**
	 * 노쇼 단건 생성
	 */
	@PostMapping
	public ApiResponse<CreateNoShowResponse> createNoShow(
		@RequestBody CreateNoShowRequest request
	) {
		CreateNoShowCommand command = request.toCommand();
		CreateNoShowResult result = noShowService.createNoShow(command);
		CreateNoShowResponse response = CreateNoShowResponse.from(result);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}

	/**
	 * 노쇼 다건 생성 (스케줄러)
	 */
	@PostMapping("/scheduler")
	public ApiResponse<CreateNoShowListResponse> createNoShowList(
		@RequestBody CreateNoShowListRequest request
	) {
		CreateNoShowListCommand command = request.toCommand();
		CreateNoShowListResult result = noShowService.createNoShowList(command);
		CreateNoShowListResponse response = CreateNoShowListResponse.from(result);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}
}
