package com.popjub.reservationservice.infrastructure.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.popjub.reservationservice.domain.entity.Reservation;
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
	public Reservation findById(UUID reservationId) {
		return jpaRepository.findById(reservationId)
			.orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
	}

	@Override
	public boolean existsByUserIdAndStoreIdAndReservationDate(Long userId, UUID storeId, LocalDate reservationDate) {
		return jpaRepository.existsByUserIdAndStoreIdAndReservationDate(userId, storeId, reservationDate);
	}
}
