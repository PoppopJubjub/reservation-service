package com.popjub.reservationservice.infrastructure.client.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.EventType;

public record ReservationNotificationRequest(
	UUID reservationId,
	Long userId,
	String storeName,
	LocalDate reservationDate,
	LocalTime startTime,
	EventType eventType
) {
}
