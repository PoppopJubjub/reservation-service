package com.popjub.reservationservice.domain.repository;

import java.time.LocalDate;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public interface ReservationRepository {

	Reservation save(Reservation reservation);

	Reservation findById(UUID reservationId);

	boolean existsByUserIdAndStoreIdAndReservationDate(
		Long userId,
		UUID storeId,
		LocalDate reservationDate);
}
