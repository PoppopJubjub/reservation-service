package com.popjub.reservationservice.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

@FeignClient(name = "store-service", url = "localhost:18082/internal/stores")
public interface StoreServiceClient {

	@GetMapping("/{timeslotId}")
	SearchTimeslotResponse getTimeslot(@PathVariable UUID timeslotId);

}
