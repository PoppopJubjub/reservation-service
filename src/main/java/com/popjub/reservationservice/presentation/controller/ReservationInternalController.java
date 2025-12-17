package com.popjub.reservationservice.presentation.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.popjub.reservationservice.application.service.NoShowService;
import com.popjub.reservationservice.application.service.ReservationService;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.infrastructure.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/reservations")
public class ReservationInternalController {

	private final RedisUtil redisUtil;
	private final NoShowService noShowService;
	private final ReservationService reservationService;

	@PostMapping("/capacities")
	public Map<UUID, Integer> getRemaining(
		@RequestBody List<UUID> timeslotIds
	) {
		return redisUtil.getRemaining(timeslotIds);
	}

	@PostMapping("/timeslots/closed")
	public void closedTimeslots(
		@RequestBody List<UUID> timeslotIds) {
		noShowService.closedTimeslots(timeslotIds);
	}

	@PostMapping("/{reservationId}/validate")
	public ReservationStatus reservationValidate(
		@PathVariable UUID reservationId,
		@RequestParam Long userId
	) {
		return reservationService.validReservation(reservationId, userId);
	}
}