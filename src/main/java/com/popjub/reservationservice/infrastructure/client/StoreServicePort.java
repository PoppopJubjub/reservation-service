package com.popjub.reservationservice.infrastructure.client;

import java.util.UUID;

import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

public interface StoreServicePort {
	SearchTimeslotResponse getTimeslot(UUID timeslotId);
}
