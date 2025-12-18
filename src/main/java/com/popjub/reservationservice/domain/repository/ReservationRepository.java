package com.popjub.reservationservice.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;

public interface ReservationRepository {

	Reservation save(Reservation reservation);

	Optional<Reservation> findById(UUID reservationId);

	Optional<Reservation> findByUserIdAndReservationIdAndStatus(
		Long userId,
		UUID reservationId,
		ReservationStatus status
	);

	boolean existsByUserIdAndStoreIdAndReservationDate(
		Long userId,
		UUID storeId,
		LocalDate reservationDate);

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

	Optional<Reservation> findByQrCode(String qrCode);

	List<Reservation> findAllByTimeslotIdInAndStatus(
		List<UUID> timeslotId,
		ReservationStatus status
	);

	Optional<Reservation> findByReservationIdAndUserIdAndStoreId(
		UUID reservationId,
		Long userId,
		UUID storeId
	);
}
