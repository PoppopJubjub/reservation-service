package com.popjub.reservationservice.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.ReservationEventPort;
import com.popjub.reservationservice.application.port.dto.ReservationCreatedEventDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventAdapter implements ReservationEventPort {

	private static final String TOPIC = "reservation.created";
	private final KafkaTemplate<String, ReservationCreatedEvent> kafkaTemplate;

	@Override
	public void publishReservationCreated(ReservationCreatedEventDto eventDto) {
		log.info("Kafka 예약 생성 이벤트 발행 - reservationId: {}", eventDto.reservationId());
		ReservationCreatedEvent event = ReservationCreatedEvent.builder()
			.reservationId(eventDto.reservationId())
			.userId(eventDto.userId())
			.storeName(eventDto.storeName())
			.reservationDate(eventDto.reservationDate())
			.reservationTime(eventDto.reservationTime())
			.friendCnt(eventDto.friendCnt())
			.build();
		kafkaTemplate.send(TOPIC, event);
	}
}
