package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

/**
 * 특정 날짜의 예약 완료된 예약 목록 조회 응답 DTO
 */
public record SearchStoreReservationByDateResult(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static SearchStoreReservationByDateResult from(Reservation reservation) {
		return new SearchStoreReservationByDateResult(
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
