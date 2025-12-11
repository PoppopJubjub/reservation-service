package com.popjub.reservationservice.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record GetRemainingRequest(
	List<UUID> timeslotIds
) {
}
