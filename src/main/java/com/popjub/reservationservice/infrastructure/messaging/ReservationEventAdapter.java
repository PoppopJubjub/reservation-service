package com.popjub.reservationservice.infrastructure.messaging;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.ReservationEventPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventAdapter implements ReservationEventPort {

	private static final String TOPIC = "reservation.created";
	private final KafkaTemplate<String, ReservationCreatedEvent> kafkaTemplate;

	@Override
	public void publishReservationCreated(
		UUID reservationId,
		Long userId,
		UUID storeId,
		UUID timeslotId,
		LocalDate reservationDate,
		Integer friendCnt,
		String qrCode
	) {
		log.info("Kafka 예약 생성 이벤트 발행 - reservationId: {}", reservationId);
		ReservationCreatedEvent event = ReservationCreatedEvent.builder()
			.reservationId(reservationId)
			.userId(userId)
			.storeId(storeId)
			.timeslotId(timeslotId)
			.reservationDate(reservationDate)
			.friendCnt(friendCnt)
			.qrCode(qrCode)
			.build();

		kafkaTemplate.send(TOPIC, event);
	}
}
