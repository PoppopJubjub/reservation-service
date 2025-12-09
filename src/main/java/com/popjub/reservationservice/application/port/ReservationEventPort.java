package com.popjub.reservationservice.application.port;

import com.popjub.reservationservice.application.port.dto.ReservationCreatedEventDto;

public interface ReservationEventPort {
	void publishReservationCreated(ReservationCreatedEventDto eventDto
	);
}
