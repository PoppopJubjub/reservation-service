package com.popjub.reservationservice.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.searchStoreReservationByFilterResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record searchStoreReservationByFilterResponse(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static searchStoreReservationByFilterResponse from(searchStoreReservationByFilterResult result) {
		return new searchStoreReservationByFilterResponse(
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
