package com.popjub.reservationservice.presentation.dto.request;

import java.util.UUID;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
	@NotNull(message = " 타임슬롯을 선택해주세요.")
	UUID timeslotId,
	@NotNull(message = "동반 인원은 필수입니다")
	@Min(value = 0, message = "동반 인원은 0명 이상이어야 합니다")
	@Max(value = 3, message = "동반 인원은 최대 3명입니다")
	Integer friendCnt

) {
	public CreateReservationCommand toCommand(Long userId) {
		return new CreateReservationCommand(
			userId,
			this.timeslotId,
			this.friendCnt
		);
	}
}
