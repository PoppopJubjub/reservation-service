package com.popjub.reservationservice.infrastructure.client.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SearchTimeslotResponse(
	UUID timeslotId,
	UUID storeId,
	LocalDate date,
	String status
) {
}
