package com.popjub.reservationservice.application.port.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record TimeslotResult(
	UUID timeslotId,
	UUID storeId,
	String storeName,
	LocalDate reservationDate,
	LocalTime reservationTime,
	TimeSlotStatus status,
	Integer capacity
) {
	public Boolean reservationTimeValid() {
		return !LocalDateTime.now().isBefore(LocalDateTime.of(this.reservationDate, this.reservationTime));
	}
}
