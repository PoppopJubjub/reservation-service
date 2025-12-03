package com.popjub.reservationservice.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.SearchStoreReservationResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record SearchStoreReservationResponse(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static SearchStoreReservationResponse from(SearchStoreReservationResult result) {
		return new SearchStoreReservationResponse(
			result.reservationId(),
			result.userId(),
			result.storeId(),
			result.timeslotId(),
			result.reservationDate(),
			result.friendCnt(),
			result.status(),
			result.createdAt()
		);
	}
}
