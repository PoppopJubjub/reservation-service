package com.popjub.reservationservice.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemainingCapacity {
	Integer value;

	public static RemainingCapacity from(Integer remaining) {
		validate(remaining);
		return new RemainingCapacity(remaining);
	}

	private static void validate(Integer value) {
		if (value < 0) {
			throw new RuntimeException("잔여석은 음수가 될 수 없습니다.");
		}
	}

	public boolean isZero() {
		return value == 0;
	}

	public boolean isNotZero() {
		return !isZero();
	}
}
