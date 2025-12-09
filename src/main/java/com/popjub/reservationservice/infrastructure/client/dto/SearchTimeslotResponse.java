package com.popjub.reservationservice.infrastructure.client.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SearchTimeslotResponse(
	UUID timeslotId,
	UUID storeId,
	String storeName,
	LocalDate date,
	LocalTime time,
	String status
) {
}
