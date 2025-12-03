package com.popjub.reservationservice.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.SearchReservationResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record SearchReservationResponse(
	UUID reservationId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static SearchReservationResponse from(SearchReservationResult result) {
		return new SearchReservationResponse(
			result.reservationId(),
			result.storeId(),
			result.timeslotId(),
			result.reservationDate(),
			result.friendCnt(),
			result.status(),
			result.createdAt()
		);
	}
}
