package com.popjub.reservationservice.infrastructure.client;

import org.springframework.stereotype.Component;

import com.popjub.reservationservice.infrastructure.client.dto.ReservationNotificationRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MockNotificationClient {
	public void sendReservationCreateNotification(ReservationNotificationRequest request) {
		log.info("알림 내용: 예약 ID={}, 유저 ID={}, 팝업 스토어 이름={}, 날짜={}, 예약시간={}, 알림 타입={}",
			request.reservationId(),
			request.userId(),
			request.storeName(),
			request.reservationDate(),
			request.startTime(),
			request.eventType());
	}
}
