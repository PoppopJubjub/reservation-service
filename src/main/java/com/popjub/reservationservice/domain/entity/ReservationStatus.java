package com.popjub.reservationservice.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

	COMPLETE("예약 확정", "예약이 정상적으로 완료되었습니다"),
	CANCELLED("예약 취소", "사용자가 예약을 취소했습니다"),
	NO_SHOW("노쇼", "예약 시간에 나타나지 않았습니다"),
	CHECKED_IN("체크인 완료", "현장 체크인이 완료되었습니다");

	private final String displayName;
	private final String description;
}
