package com.popjub.reservationservice.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

@FeignClient(name = "store-service", url = "/internal/stores")
@Profile("prod")
public interface StoreServiceClient extends StoreServicePort {

	@Override
	@GetMapping("/{timeslotId}")
	SearchTimeslotResponse getTimeslot(@PathVariable UUID timeslotId);

}
