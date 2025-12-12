package com.popjub.reservationservice.infrastructure.client.dto;

import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.EventType;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record CheckInNotificationRequest(
	UUID reservationId,
	Long userId,
	ReservationStatus status,
	EventType eventType
) {
}
