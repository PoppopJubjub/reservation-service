package com.popjub.reservationservice.application.valid;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.service.NoShowService;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationValidator {

	private final NoShowService noShowService;
	private final ReservationRepository reservationRepository;

	/**
	 * 예약 생성 전 검증
	 * 1. NoShow 제한 검증
	 * 2. 중복 예약 검증
	 */
	public void validate(CreateReservationCommand command, UUID storeId,
		LocalDate reservationDate) {

		validateNoShowRestriction(command.userId());
		validateNoDuplicateReservation(command.userId(), storeId, reservationDate);
	}

	/**
	 * NoShow 제한 검증
	 */
	private void validateNoShowRestriction(Long userId) {
		if (noShowService.isRestricted(userId)) {
			throw new ReservationCustomException(ReservationErrorCode.NO_SHOW_RESTRICTED);
		}
	}

	/**
	 * 중복 예약 검증
	 */
	private void validateNoDuplicateReservation(Long userId, UUID storeId, LocalDate reservationDate) {
		if (reservationRepository.existsByUserIdAndStoreIdAndReservationDateAndStatusNot(
			userId,
			storeId,
			reservationDate,
			ReservationStatus.CANCELLED)
		) {
			throw new ReservationCustomException(ReservationErrorCode.DUPLICATE_RESERVATION);
		}
	}
}
