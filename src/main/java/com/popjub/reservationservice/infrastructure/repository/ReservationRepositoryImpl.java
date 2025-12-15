package com.popjub.reservationservice.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.domain.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

	private final ReservationJpaRepository jpaRepository;

	@Override
	public Reservation save(Reservation reservation) {
		return jpaRepository.save(reservation);
	}

	@Override
	public Optional<Reservation> findById(UUID reservationId) {
		return jpaRepository.findById(reservationId);
	}

	@Override
	public Optional<Reservation> findByUserIdAndReservationIdAndStatus(Long userId, UUID reservationId,
		ReservationStatus status) {
		return jpaRepository.findByUserIdAndReservationIdAndStatus(userId, reservationId, status);
	}

	@Override
	public boolean existsByUserIdAndStoreIdAndReservationDate(Long userId, UUID storeId, LocalDate reservationDate) {
		return jpaRepository.existsByUserIdAndStoreIdAndReservationDate(userId, storeId, reservationDate);
	}

	@Override
	public Page<Reservation> findAllByUserId(Long userId, Pageable pageable) {
		return jpaRepository.findAllByUserId(userId, pageable);
	}

	@Override
	public Page<Reservation> findAllByStoreId(UUID storeId, Pageable pageable) {
		return jpaRepository.findAllByStoreId(storeId, pageable);
	}

	@Override
	public Page<Reservation> findAllByStoreIdAndStatusAndReservationDate(UUID storeId, ReservationStatus status,
		LocalDate reservationDate, Pageable pageable) {
		return jpaRepository.findAllByStoreIdAndStatusAndReservationDate(storeId, status, reservationDate, pageable);
	}

	@Override
	public Optional<Reservation> findByQrCode(String qrCode) {
		return jpaRepository.findByQrCode(qrCode);
	}

	@Override
	public List<Reservation> findAllByTimeslotIdInAndStatus(List<UUID> timeslotId, ReservationStatus status) {
		return jpaRepository.findAllByTimeslotIdInAndStatus(timeslotId, status);
	}
}
