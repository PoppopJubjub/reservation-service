package com.popjub.reservationservice.application.port;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.popjub.reservationservice.domain.vo.RemainingCapacity;

public interface RemainingPort {
	RemainingCapacity decrease(UUID timeslotId, int count);

	RemainingCapacity increase(UUID timeslotId, int count);

	Map<UUID, Integer> getRemaining(List<UUID> timeslotIds);
}
