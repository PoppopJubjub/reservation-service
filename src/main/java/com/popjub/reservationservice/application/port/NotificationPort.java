package com.popjub.reservationservice.application.port;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public interface NotificationPort {
	void sendReservationCreateNotification(
		UUID reservationId,
		Long userId,
		String storeName,
		LocalDate reservationDate,
		LocalTime startTime
	);

	void sendNoShowNotification(
		Reservation reservation,
		Integer noShowCount
	);

	void sendCheckInNotification(
		Reservation reservation
	);
}
