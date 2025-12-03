package com.popjub.reservationservice.domain.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.Reservation;

public interface ReservationRepository {

	Reservation save(Reservation reservation);

	Optional<Reservation> findById(UUID reservationId);

	boolean existsByUserIdAndStoreIdAndReservationDate(
		Long userId,
		UUID storeId,
		LocalDate reservationDate);
}
