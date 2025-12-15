package com.popjub.reservationservice.application.port;

import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;
import com.popjub.reservationservice.application.port.dto.TimeslotResult;

public interface StoreServicePort {
	TimeslotResult getTimeslot(UUID timeslotId);

	boolean validateCheckIn(UUID storeId, UUID timeslotId, Long userId);

	void updateTimeslotStatus(UUID timeslotId, TimeSlotStatus status);
}
