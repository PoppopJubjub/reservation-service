package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

/**
 * 스토어의 예약 완료된 예약 목록 조회 응답 DTO
 */
public record SearchStoreReservationResult(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static SearchStoreReservationResult from(Reservation reservation) {
		return new SearchStoreReservationResult(
			reservation.getReservationId(),
			reservation.getUserId(),
			reservation.getStoreId(),
			reservation.getTimeslotId(),
			reservation.getReservationDate(),
			reservation.getFriendCnt(),
			reservation.getStatus(),
			reservation.getCreatedAt()
		);
	}
}
