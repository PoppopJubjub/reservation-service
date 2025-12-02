package com.popjub.reservationservice.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.service.ReservationService;
import com.popjub.reservationservice.presentation.dto.request.CreateReservationRequest;
import com.popjub.reservationservice.presentation.dto.response.CreateReservationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ApiResponse<CreateReservationResponse> createReservation(
		@RequestBody CreateReservationRequest request,
		@RequestHeader("X-User-Id") Long userId
	) {
		CreateReservationCommand command = request.toCommand(userId);
		CreateReservationResult result = reservationService.createReservation(command);
		CreateReservationResponse response = CreateReservationResponse.from(result);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}
}