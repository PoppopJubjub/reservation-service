package com.popjub.reservationservice.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.command.CreateNoShowCommand;
import com.popjub.reservationservice.application.dto.result.CreateNoShowResult;
import com.popjub.reservationservice.domain.entity.NoShow;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.repository.NoShowRepository;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoShowService {

	private final NoShowRepository noShowRepository;
	private final ReservationRepository reservationRepository;

	@Transactional
	public CreateNoShowResult createNoShow(CreateNoShowCommand command) {
		if (noShowRepository.existsByReservationId(command.reservationId())) {
			throw new ReservationCustomException(ReservationErrorCode.NO_SHOW_ALREADY_EXISTS);
		}

		Reservation reservation = reservationRepository.findById(command.reservationId())
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.NOT_FOUNT_RESERVATION));

		reservation.noShowReservation();
		reservationRepository.save(reservation);

		NoShow noShow = command.toEntity();
		noShowRepository.save(noShow);
		return CreateNoShowResult.from(noShow);
	}
}
