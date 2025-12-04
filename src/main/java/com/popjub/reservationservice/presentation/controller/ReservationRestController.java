package com.popjub.reservationservice.presentation.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.common.enums.SuccessCode;
import com.popjub.common.response.ApiResponse;
import com.popjub.common.response.PageResponse;
import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CancelReservationResult;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchStoreReservationResult;
import com.popjub.reservationservice.application.dto.result.searchStoreReservationByFilterResult;
import com.popjub.reservationservice.application.service.ReservationService;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.presentation.dto.request.CreateReservationRequest;
import com.popjub.reservationservice.presentation.dto.response.CancelReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.CreateReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.SearchReservationDetailResponse;
import com.popjub.reservationservice.presentation.dto.response.SearchReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.SearchStoreReservationResponse;
import com.popjub.reservationservice.presentation.dto.response.searchStoreReservationByFilterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationRestController {

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

	/**
	 * 예약 목록 조회
	 * 본인의 예약만 조회
	 */
	@GetMapping()
	public ApiResponse<PageResponse<SearchReservationResponse>> searchReservation(
		@RequestHeader("X-User-Id") Long userId,
		@PageableDefault(
			size = 10,
			sort = "createdAt",
			direction = Sort.Direction.ASC
		) Pageable pageable
	) {
		Page<SearchReservationResult> result = reservationService.searchReservation(userId, pageable);
		Page<SearchReservationResponse> response = result.map(SearchReservationResponse::from);
		PageResponse<SearchReservationResponse> pageResponse = PageResponse.from(response);
		return ApiResponse.of(SuccessCode.OK, pageResponse);
	}

	/**
	 * 예약 목록 조회
	 * 스토어의 예약된 전체 예약 목록 조회
	 * TODO: 스토어 매니저 ID 검증 로직
	 */
	@GetMapping("/store/{storeId}")
	public ApiResponse<PageResponse<SearchStoreReservationResponse>> searchStoreReservation(
		@PathVariable UUID storeId,
		@PageableDefault(
			size = 50,
			sort = "createdAt",
			direction = Sort.Direction.ASC
		) Pageable pageable
	) {
		Page<SearchStoreReservationResult> result = reservationService.searchStoreReservation(storeId,
			pageable);
		Page<SearchStoreReservationResponse> response = result.map(SearchStoreReservationResponse::from);
		PageResponse<SearchStoreReservationResponse> pageResponse = PageResponse.from(response);
		return ApiResponse.of(SuccessCode.OK, pageResponse);
	}

	/**
	 * 예약 목록 조회
	 * 스토어의 특정 날짜와 예약 상태를 기준으로 예약 목록 조회
	 * TODO: 스토어 매니저 ID 검증 로직
	 */
	@GetMapping("/store/{storeId}/filter")
	public ApiResponse<PageResponse<searchStoreReservationByFilterResponse>> SearchStoreReservationByDateResponse(
		@PathVariable UUID storeId,
		@RequestParam LocalDate reservationDate,
		@RequestParam ReservationStatus status,
		@PageableDefault(
			size = 50,
			sort = "createdAt",
			direction = Sort.Direction.ASC
		) Pageable pageable
	) {
		Page<searchStoreReservationByFilterResult> result = reservationService.searchStoreReservationByFilter(
			storeId, status, reservationDate, pageable);
		Page<searchStoreReservationByFilterResponse> response = result.map(
			searchStoreReservationByFilterResponse::from);
		PageResponse<searchStoreReservationByFilterResponse> pageResponse = PageResponse.from(response);
		return ApiResponse.of(SuccessCode.OK, pageResponse);
	}
}