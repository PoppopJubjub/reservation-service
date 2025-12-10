package com.popjub.reservationservice.infrastructure.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	Page<Reservation> findAllByUserId(
		Long userId,
		Pageable pageable
	);

	Page<Reservation> findAllByStoreId(
		UUID storeId,
		Pageable pageable
	);

	Page<Reservation> findAllByStoreIdAndStatusAndReservationDate(
		UUID storeId,
		ReservationStatus status,
		LocalDate reservationDate,
		Pageable pageable
	);

	Optional<Reservation> findByQrCode(String QrCode);
}
