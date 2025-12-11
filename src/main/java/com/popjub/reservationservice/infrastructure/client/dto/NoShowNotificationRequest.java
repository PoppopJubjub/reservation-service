package com.popjub.reservationservice.infrastructure.client.dto;

import java.util.UUID;

import com.popjub.reservationservice.application.port.dto.EventType;

public record NoShowNotificationRequest(
	UUID reservationId,
	Long userId,
	Integer noShowCount,
	EventType eventType
) {
}
