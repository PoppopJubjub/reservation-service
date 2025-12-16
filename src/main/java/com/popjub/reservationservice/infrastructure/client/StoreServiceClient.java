package com.popjub.reservationservice.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;
import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

@FeignClient(name = "store-service", url = "localhost:18082/internal/stores")
public interface StoreServiceClient {

	@GetMapping("/{timeslotId}")
	SearchTimeslotResponse getTimeslot(@PathVariable UUID timeslotId);

	@GetMapping("/checkin/validate")
	boolean validateCheckIn(
		@RequestParam("storeId") UUID storeId,
		@RequestParam("timeslotId") UUID timeslotId,
		@RequestParam("userId") Long userId
	);

	@PostMapping("/timeslot/update-status")
	void updateTimeslotStatus(
		@RequestParam("timeslotId") UUID timeslotId,
		@RequestParam("status") TimeSlotStatus status
	);
}
