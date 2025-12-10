package com.popjub.reservationservice.domain.repository;

import java.util.UUID;

import com.popjub.reservationservice.domain.entity.NoShow;

public interface NoShowRepository {

	boolean existsByReservationId(UUID reservationId);

	NoShow save(NoShow noShow);
}
