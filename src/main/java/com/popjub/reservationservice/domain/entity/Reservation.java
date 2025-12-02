package com.popjub.reservationservice.domain.entity;

import java.util.UUID;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_reservation")
@NoArgsConstructor
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID reservationId;

	private Long userId;

	private UUID timeslotId;

	private Integer friendCnt;

	private String qrCode;

	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

}