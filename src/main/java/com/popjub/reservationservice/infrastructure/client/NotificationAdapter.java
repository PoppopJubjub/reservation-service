package com.popjub.reservationservice.infrastructure.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.NotificationPort;
import com.popjub.reservationservice.application.port.dto.EventType;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.infrastructure.client.dto.CheckInNotificationRequest;
import com.popjub.reservationservice.infrastructure.client.dto.NoShowNotificationRequest;
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
	public void sendReservationCreateNotification(UUID reservationId, Long userId, String storeName,
		LocalDate reservationDate, LocalTime startTime) {
		ReservationNotificationRequest request = new ReservationNotificationRequest(
			reservationId,
			userId,
			storeName,
			reservationDate,
			startTime,
			EventType.RESERVATION_COMPLETED
		);

		if (flag) {
			notificationServiceClient.sendReservationCreateNotification(request);
		}
		mockNotificationClient.sendReservationCreateNotification(request);
	}

	@Override
	public void sendNoShowNotification(Reservation reservation, Integer noShowCount) {
		NoShowNotificationRequest request = new NoShowNotificationRequest(
			reservation.getReservationId(),
			reservation.getUserId(),
			noShowCount,
			EventType.NO_SHOW_WARNING
		);

		if (flag) {
			notificationServiceClient.sendNoShowNotification(request);
		}
		mockNotificationClient.sendNoShowNotification(request);
	}

	@Override
	public void sendCheckInNotification(Reservation reservation) {
		CheckInNotificationRequest request = new CheckInNotificationRequest(
			reservation.getReservationId(),
			reservation.getUserId(),
			reservation.getStatus(),
			EventType.CHECKIN_COMPLETED
		);

		if (flag) {
			notificationServiceClient.sendCheckInNotification(request);
		}
		mockNotificationClient.sendCheckInNotification(request);
	}
}
