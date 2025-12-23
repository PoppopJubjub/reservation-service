package com.popjub.reservationservice.application.valid;

import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.global.exception.ReservationCustomException;
import com.popjub.reservationservice.global.exception.ReservationErrorCode;

@Component
public class TimeslotValidator {
	/**
	 * 타임슬롯 예약 가능 여부 검증
	 * 1. 예약 시간 유효성 검증
	 * 2. 타임슬롯 상태 검증
	 */
	public void validateTimeslot(TimeslotResult timeslot) {
		// 1. 예약 시간 유효성 검증
		validateReservationTime(timeslot);

		// 2. 타임슬롯 상태 검증
		validateTimeslotStatus(timeslot);
	}

	/**
	 * 예약 시간 유효성 검증
	 */
	private void validateReservationTime(TimeslotResult timeslot) {
		if (timeslot.reservationTimeValid()) {
			throw new ReservationCustomException(ReservationErrorCode.PAST_RESERVATION_TIME);
		}
	}

	/**
	 * 타임슬롯 상태 검증 (AVAILABLE 여부)
	 */
	private void validateTimeslotStatus(TimeslotResult timeslot) {
		if (timeslot.status().isNotAvailable()) {
			throw new ReservationCustomException(ReservationErrorCode.TIMESLOT_NOT_AVAILABLE);
		}
	}
}
