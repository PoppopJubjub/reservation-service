package com.popjub.reservationservice.application.port;

import java.time.LocalDate;
import java.util.UUID;

public interface ReservationEventPort {
	void publishReservationCreated(
		UUID reservationId,
		Long userId,
		UUID storeId,
		UUID timeslotId,
		LocalDate reservationDate,
		Integer friendCnt,
		String qrCode
	);
}
