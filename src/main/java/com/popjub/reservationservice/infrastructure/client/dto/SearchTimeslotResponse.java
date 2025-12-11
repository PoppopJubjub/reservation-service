package com.popjub.reservationservice.infrastructure.client.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;

public record SearchTimeslotResponse(
	UUID timeslotId,
	UUID storeId,
	String storeName,
	LocalDate reservationDate,
	LocalTime reservationTime,
	TimeSlotStatus status,
	Integer capacity
) {
}
