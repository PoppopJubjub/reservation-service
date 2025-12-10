package com.popjub.reservationservice.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID reservationId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private UUID storeId;

	@Column(nullable = false)
	private UUID timeslotId;

	@Column(nullable = false)
	private Integer friendCnt;

	@Column(nullable = false)
	private LocalDate reservationDate;

	@Column(nullable = false)
	private String qrCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReservationStatus status;

	@Builder(access = AccessLevel.PRIVATE)
	private Reservation(
		Long userId,
		UUID storeId,
		UUID timeslotId,
		Integer friendCnt,
		LocalDate reservationDate,
		String qrCode
	) {
		this.userId = userId;
		this.storeId = storeId;
		this.timeslotId = timeslotId;
		this.friendCnt = friendCnt;
		this.reservationDate = reservationDate;
		this.qrCode = qrCode;
		this.status = ReservationStatus.COMPLETE;
	}

	public static Reservation of(
		Long userId,
		UUID storeId,
		UUID timeslotId,
		Integer friendCnt,
		LocalDate reservationDate,
		String qrCode
	) {
		return Reservation.builder()
			.userId(userId)
			.storeId(storeId)
			.timeslotId(timeslotId)
			.friendCnt(friendCnt)
			.reservationDate(reservationDate)
			.qrCode(qrCode)
			.build();
	}

	public void cancelReservation() {
		this.status = ReservationStatus.CANCELLED;
	}

	public void noShowReservation() {
		this.status = ReservationStatus.NO_SHOW;
	}
}