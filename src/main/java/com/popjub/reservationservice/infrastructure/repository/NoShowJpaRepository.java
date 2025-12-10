package com.popjub.reservationservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popjub.reservationservice.domain.entity.NoShow;

public interface NoShowJpaRepository extends JpaRepository<NoShow, UUID> {

	boolean existsByReservationId(UUID reservationId);
}
