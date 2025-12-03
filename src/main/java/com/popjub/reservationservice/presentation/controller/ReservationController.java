package com.popjub.reservationservice.presentation.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CancelReservationResult;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.application.service.ReservationService;
import com.popjub.reservationservice.presentation.dto.request.CreateReservationRequest;
import com.popjub.reservationservice.presentation.dto.response.CancelReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.CreateReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.SearchReservationDetailResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ApiResponse<CreateReservationResponse> createReservation(
		@Valid @RequestBody CreateReservationRequest request,
		@RequestHeader("X-User-Id") Long userId
	) {
		CreateReservationCommand command = request.toCommand(userId);
		CreateReservationResult result = reservationService.createReservation(command);
		CreateReservationResponse response = CreateReservationResponse.from(result);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}

	@GetMapping("/{reservationId}")
	public ApiResponse<SearchReservationDetailResponse> searchReservationDetail(
		@PathVariable UUID reservationId
	) {
		SearchReservationDetailResult result = reservationService.searchReservationDetail(reservationId);
		SearchReservationDetailResponse response = SearchReservationDetailResponse.from(result);
		return ApiResponse.of(SuccessCode.OK, response);
	}
	
	@PutMapping("/{reservationId}")
	public ApiResponse<CancelReservationResponse> cancelReservationResponse(
		@PathVariable UUID reservationId,
		@RequestHeader("X-User-Id") Long userId
	) {
		CancelReservationResult result = reservationService.cancelReservation(reservationId, userId);
		CancelReservationResponse response = CancelReservationResponse.from(result);
		return ApiResponse.of(SuccessCode.OK, response);
	}
}