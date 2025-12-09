package com.popjub.reservationservice.application.port.dto;

public enum TimeSlotStatus {
	AVAILABLE,
	FULL,
	CLOSED,
	;

	public boolean isAvailable() {
		return this == AVAILABLE;
	}

	public boolean isNotAvailable() {
		return !isAvailable();
	}
}
