package com.popjub.reservationservice.infrastructure.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.exception.ReservationCustomException;
import com.popjub.reservationservice.exception.ReservationErrorCode;
import com.popjub.reservationservice.infrastructure.client.StoreAdapter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, Integer> redisTemplate;
	private final StoreAdapter storeAdapter;

	public Map<UUID, Integer> getRemaining(List<UUID> timeslotIds) {
		Map<UUID, Integer> result = new HashMap<>();

		List<String> keys = timeslotIds.stream()
			.map(this::generateKey)
			.toList();

		List<Integer> values = redisTemplate.opsForValue().multiGet(keys);

		for (int i = 0; i < timeslotIds.size(); i++) {
			UUID timeslotId = timeslotIds.get(i);
			Integer remaining = values != null ? values.get(i) : null;

			if (remaining == null) {
				remaining = initializeCapacity(timeslotId);
			}
			result.put(timeslotId, remaining);
		}
		return result;
	}

	/**
	 * 예약 취소시 잔여석 증가 메소드
	 */
	public Integer increaseRemaining(UUID timeslotId) {
		String key = generateKey(timeslotId);
		Long remaining = redisTemplate.opsForValue().increment(key);

		return remaining.intValue();
	}

	/**
	 * 예약 성공시 잔여석 감소 메소드
	 */
	public Integer decreaseRemaining(UUID timeslotId) {
		String key = generateKey(timeslotId);
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			initializeCapacity(timeslotId);
		}
		Long remaining = redisTemplate.opsForValue().decrement(key);
		if (remaining < 0) {
			redisTemplate.opsForValue().increment(key);
			throw new ReservationCustomException(ReservationErrorCode.NO_AVAILABLE_SEAT);
		}
		return remaining.intValue();
	}

	/**
	 * Redis 초기화 (Lazy init)
	 * 1. Timeslot 서비스에서 capacity 조회
	 * 2. Redis에 저장
	 */
	private Integer initializeCapacity(UUID timeslotId) {
		TimeslotResult timeslot = storeAdapter.getTimeslot(timeslotId);
		Integer capacity = timeslot.capacity();

		String key = generateKey(timeslotId);
		redisTemplate.opsForValue().set(key, capacity);
		return capacity;
	}

	private String generateKey(UUID timeslotId) {
		return "timeslot:remaining:" + timeslotId;
	}
}
