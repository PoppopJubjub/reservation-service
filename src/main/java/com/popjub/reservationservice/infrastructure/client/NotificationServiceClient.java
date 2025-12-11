package com.popjub.reservationservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.popjub.reservationservice.infrastructure.client.dto.NoShowNotificationRequest;
import com.popjub.reservationservice.infrastructure.client.dto.ReservationNotificationRequest;

@FeignClient(name = "notification-service", url = "/internal/notifications")
public interface NotificationServiceClient {

	@PostMapping("/reservation/create")
	void sendReservationCreateNotification(
		@RequestBody ReservationNotificationRequest request
	);

	@PostMapping("/no-show")
	void sendNoShowNotification(
		@RequestBody NoShowNotificationRequest request
	);
}
