package com.popjub.reservationservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.popjub.reservationservice.domain.entity.NoShow;
import com.popjub.reservationservice.domain.repository.NoShowRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoShowRepositoryImpl implements NoShowRepository {

	private final NoShowJpaRepository jpaRepository;

	@Override
	public boolean existsByReservationId(UUID reservationId) {
		return jpaRepository.existsByReservationId(reservationId);

	}

	@Override
	public NoShow save(NoShow noShow) {
		return jpaRepository.save(noShow);
	}
}
