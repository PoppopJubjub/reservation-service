package com.popjub.reservationservice.infrastructure.messaging;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent {
	private UUID reservationId;
	private Long userId;
	private String storeName;
	private LocalDate reservationDate;
	private LocalTime reservationTime;
	private Integer friendCnt;
	private String qrCode;
}
