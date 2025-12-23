package com.popjub.reservationservice.application.service;

import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.port.NotificationPort;
import com.popjub.reservationservice.global.annotation.DistributedLock;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationFacade {
	private final ReservationService reservationService;
	private final NotificationPort notificationPort;

	@DistributedLock(key = "'timeslot:' + #command.timeslotId()", waitTime = 5L, leaseTime = 3L)
	public CreateReservationResult createReservation(CreateReservationCommand command) {
		CreateReservationResult result = reservationService.createReservation(command);
		notificationPort.sendReservationCreateNotification(
			result.reservationId(),
			result.userId(),
			result.storeName(),
			result.reservationDate(),
			result.reservationTime()
		);
		return result;
	}
}