package com.popjub.reservationservice.infrastructure.client;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

@Component
@Profile("dev")
public class TestStoreServiceClient implements StoreServicePort {
	/**
	 * 예약 생성 API 테스트를 위한 임시 데이터
	 **/
	@Override
	public SearchTimeslotResponse getTimeslot(UUID timeslotId) {
		return new SearchTimeslotResponse(
			timeslotId,
			UUID.fromString("650e8400-e29b-41d4-a716-446655440000"),
			LocalDate.now().plusDays(1),
			"AVAILABLE"
		);
	}
}
