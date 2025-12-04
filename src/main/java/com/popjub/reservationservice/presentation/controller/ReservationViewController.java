package com.popjub.reservationservice.presentation.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.application.service.ReservationService;
import com.popjub.reservationservice.presentation.dto.response.SearchReservationDetailResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationViewController {

	private final ReservationService reservationService;

	@GetMapping("/{reservationId}/view")
	public String reservationDetailView(
		@PathVariable UUID reservationId,
		Model model
	) {
		SearchReservationDetailResult result = reservationService.searchReservationDetail(reservationId);
		SearchReservationDetailResponse response = SearchReservationDetailResponse.from(result);
		model.addAttribute("response", response);
		return "reservationDetail";
	}
}
