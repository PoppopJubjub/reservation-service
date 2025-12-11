package com.popjub.reservationservice.domain.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import com.popjub.reservationservice.domain.entity.NoShow;

public interface NoShowRepository {

	boolean existsByReservationId(UUID reservationId);

	NoShow save(NoShow noShow);

	Integer countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime localDateTime);
}
