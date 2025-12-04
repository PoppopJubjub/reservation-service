package com.popjub.reservationservice.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public record SearchReservationDetailResponse(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	String qrCode,
	String qrCodeImage,
	ReservationStatus status
) {
	public static SearchReservationDetailResponse from(SearchReservationDetailResult result) {
		return new SearchReservationDetailResponse(
			result.reservationId(),
			result.userId(),
			result.storeId(),
			result.timeslotId(),
			result.reservationDate(),
			result.friendCnt(),
			result.qrCode(),
			result.qrCodeImage(),
			result.status()
		);
	}
}
