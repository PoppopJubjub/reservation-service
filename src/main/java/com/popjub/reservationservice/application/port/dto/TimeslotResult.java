package com.popjub.reservationservice.application.port.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TimeslotResult(
	UUID timeslotId,
	UUID storeId,
	LocalDate date,
	TimeSlotStatus status
) {
}
