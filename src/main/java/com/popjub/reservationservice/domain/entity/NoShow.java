package com.popjub.reservationservice.domain.entity;

import java.util.UUID;

import com.popjub.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "p_no_show")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoShow extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID noShowId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private UUID reservationId;

	@Builder(access = AccessLevel.PRIVATE)
	private NoShow(Long userId, UUID reservationId) {
		this.userId = userId;
		this.reservationId = reservationId;
	}

	public static NoShow of(Long userId, UUID reservationId) {
		return NoShow.builder()
			.userId(userId)
			.reservationId(reservationId)
			.build();
	}
}
