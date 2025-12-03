package com.popjub.reservationservice.infrastructure.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

	boolean existsByUserIdAndStoreIdAndReservationDate(
		Long userId,
		UUID storeId,
		LocalDate reservationDate
	);

	Optional<Reservation> findByUserIdAndReservationIdAndStatus(
		Long userId,
		UUID reservationId,
		ReservationStatus status
	);
}
