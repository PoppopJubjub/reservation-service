package com.popjub.reservationservice.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.popjub.reservationservice.application.dto.command.CreateReservationCommand;
import com.popjub.reservationservice.application.dto.result.CancelReservationResult;
import com.popjub.reservationservice.application.dto.result.CreateReservationResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationDetailResult;
import com.popjub.reservationservice.application.dto.result.SearchReservationResult;
import com.popjub.reservationservice.domain.entity.Reservation;
import com.popjub.reservationservice.domain.entity.ReservationStatus;
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

	public SearchReservationDetailResult searchReservationDetail(UUID reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
		return SearchReservationDetailResult.from(reservation);
	}

	@Transactional
	public CancelReservationResult cancelReservation(UUID reservationId, Long userId) {
		Reservation reservation = reservationRepository.findByUserIdAndReservationIdAndStatus(userId, reservationId,
				ReservationStatus.COMPLETE)
			.orElseThrow(() -> new IllegalArgumentException("본인의 예약이고, 예약 확정 상태일 경우만 취소 할 수 있습니다."));
		reservation.cancelReservation();
		reservationRepository.save(reservation);
		return CancelReservationResult.from(reservation);
	}

	public Page<SearchReservationResult> searchReservation(Long userId, Pageable pageable) {
		Page<Reservation> reservationPage = reservationRepository.findAllByUserId(userId, pageable);
		return reservationPage.map(SearchReservationResult::from);
	}

	private String generatedQrcode() {
		return UUID.randomUUID().toString();
	}
}
