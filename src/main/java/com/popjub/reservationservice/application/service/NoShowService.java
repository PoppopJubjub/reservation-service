package com.popjub.reservationservice.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.command.CreateNoShowCommand;
import com.popjub.reservationservice.application.dto.command.CreateNoShowListCommand;
import com.popjub.reservationservice.application.dto.result.CreateNoShowListResult;
import com.popjub.reservationservice.application.dto.result.CreateNoShowResult;
import com.popjub.reservationservice.domain.entity.NoShow;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.repository.NoShowRepository;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			.orElseThrow(() -> new ReservationCustomException(ReservationErrorCode.NOT_FOUND_RESERVATION));

		reservation.noShowReservation();
		reservationRepository.save(reservation);

		NoShow noShow = command.toEntity();
		noShowRepository.save(noShow);
		return CreateNoShowResult.from(noShow);
	}

	@Transactional
	public CreateNoShowListResult createNoShowList(CreateNoShowListCommand command) {
		List<CreateNoShowResult> listResult = new ArrayList<>();

		for (CreateNoShowListCommand.NoShowList list : command.noShowList()) {
			try {
				CreateNoShowCommand noShowCommand = new CreateNoShowCommand(
					list.userId(),
					list.reservationId()
				);
				CreateNoShowResult result = createNoShow(noShowCommand);
				listResult.add(result);
			} catch (Exception e) {
				log.error("노쇼 처리 실패 - userId: {}, reservationId: {}, error: {}",
					list.userId(), list.reservationId(), e.getMessage());
			}
		}
		return CreateNoShowListResult.of(listResult);
	}
}
