package com.popjub.reservationservice.application.port;

import java.time.LocalTime;

import com.popjub.reservationservice.domain.entity.Reservation;

public interface NotificationPort {
	void sendReservationCreateNotification(
		Reservation reservation,
		String storeName,
		LocalTime startTime
	);

	void sendNoShowNotification(
		Reservation reservation,
		Integer noShowCount
	);
}
