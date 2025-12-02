package com.popjub.reservationservice.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.repository.ReservationRepository;
import com.popjub.reservationservice.infrastructure.client.StoreServicePort;
import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final StoreServicePort storeServicePort;

	@Transactional
	public CreateReservationResult createReservation(CreateReservationCommand command) {

		SearchTimeslotResponse searchTimeslotResponse = storeServicePort.getTimeslot(command.timeslotId());

		if (!searchTimeslotResponse.status().equals("AVAILABLE")) {
			throw new IllegalArgumentException("해당 타임슬롯은 현재 예약가능 상태가 아닙니다.");
		}

		if (reservationRepository.existsByUserIdAndStoreIdAndReservationDate(
			command.userId(),
			searchTimeslotResponse.storeId(),
			searchTimeslotResponse.date())) {
			throw new IllegalArgumentException("이미 해당 날짜에 팝업 스토어 예약이 존재합니다.");
		}

		Reservation reservation = command.toEntity(searchTimeslotResponse, generatedQrcode());
		reservationRepository.save(reservation);
		return CreateReservationResult.from(reservation);
	}

	private String generatedQrcode() {
		return UUID.randomUUID().toString();
	}
}
