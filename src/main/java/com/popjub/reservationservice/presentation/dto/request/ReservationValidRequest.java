package com.popjub.reservationservice.presentation.dto.request;

import java.util.UUID;

public record ReservationValidRequest(
	UUID reservationId,
	Long userId,
	UUID storeId
) {
}
