package com.popjub.reservationservice.application.port;

import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.TimeslotResult;

public interface StoreServicePort {
	TimeslotResult getTimeslot(UUID timeslotId);
}
