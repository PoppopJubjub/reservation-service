package com.popjub.reservationservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.popjub.reservationservice.infrastructure.client.dto.CheckInNotificationRequest;
import com.popjub.reservationservice.infrastructure.client.dto.NoShowNotificationRequest;
import com.popjub.reservationservice.infrastructure.client.dto.ReservationNotificationRequest;

@FeignClient(name = "notification-service", url = "localhost:18082/internal/notifications")
public interface NotificationServiceClient {

	@PostMapping("/reservation/create")
	void sendReservationCreateNotification(
		@RequestBody ReservationNotificationRequest request
	);

	@PostMapping("reservation/no-show")
	void sendNoShowNotification(
		@RequestBody NoShowNotificationRequest request
	);

	@PostMapping("reservation/check-in")
	void sendCheckInNotification(
		@RequestBody CheckInNotificationRequest request
	);
}
