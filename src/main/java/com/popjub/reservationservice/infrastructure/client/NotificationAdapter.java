package com.popjub.reservationservice.infrastructure.client;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.NotificationPort;
import com.popjub.reservationservice.application.port.dto.EventType;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.infrastructure.client.dto.ReservationNotificationRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {

	private final NotificationServiceClient notificationServiceClient;
	private final MockNotificationClient mockNotificationClient;

	@Value("${feature-flag.store-service}")
	boolean flag;

	@Override
	public void sendReservationCreateNotification(Reservation reservation, String storeName, LocalTime startTime) {
		ReservationNotificationRequest request = new ReservationNotificationRequest(
			reservation.getReservationId(),
			reservation.getUserId(),
			storeName,
			reservation.getReservationDate(),
			startTime,
			EventType.RESERVATION_COMPLETED
		);

		if (flag) {
			notificationServiceClient.sendReservationCreateNotification(request);
		}
		mockNotificationClient.sendReservationCreateNotification(request);
	}
}
