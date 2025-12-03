package com.popjub.reservationservice.application.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

/**
 * 스토어의 특정 날짜와 예약 상태를 기준으로 예약 목록 조회 응답 DTO
 */
public record searchStoreReservationByFilterResult(
	UUID reservationId,
	Long userId,
	UUID storeId,
	UUID timeslotId,
	LocalDate reservationDate,
	Integer friendCnt,
	ReservationStatus status,
	LocalDateTime createdAt
) {
	public static searchStoreReservationByFilterResult from(Reservation reservation) {
		return new searchStoreReservationByFilterResult(
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
