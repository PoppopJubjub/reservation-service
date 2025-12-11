package com.popjub.reservationservice.infrastructure.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.dto.TimeSlotStatus;
import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

@Component
public class MockStoreClient {
	/**
	 * 예약 생성 API 테스트를 위한 임시 데이터
	 **/
	public SearchTimeslotResponse getTimeslot(UUID timeslotId) {
		return new SearchTimeslotResponse(
			timeslotId,
			UUID.fromString("650e8400-e29b-41d4-a716-446655440000"),
			"디즈니 팝업 스토어",
			LocalDate.now().plusDays(1),
			LocalTime.of(12, 0, 0),
			TimeSlotStatus.AVAILABLE,
			60
		);
	}
}
