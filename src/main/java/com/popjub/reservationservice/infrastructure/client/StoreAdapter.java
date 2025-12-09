package com.popjub.reservationservice.infrastructure.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.popjub.reservationservice.application.port.StoreServicePort;
import com.popjub.reservationservice.application.port.dto.TimeslotResult;
import com.popjub.reservationservice.infrastructure.client.dto.SearchTimeslotResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 오전 9시까지는 dev로 오전 9시 이후부터는 prod 버전으로 배포되어야 하는 상황
// 오전 9시까지는 개발팀이 dev로 운영하다가 9시 땡하면 CD(Delivery)를 마친이 후, prod 재시작
// 런타임에 재시작해야하는 어려움을 해결하기 위한 컨셉 feature-flag
// flag만 두고 설정만 변경하여 코드는 재 컴파일 하지 않고 배포 하는 형태
// 12-factor의 "설정콰 코드 분리"라는 원칙에 따라서 해결 방법을 고민
@Slf4j
@Component
@RequiredArgsConstructor
public class StoreAdapter implements StoreServicePort {
	private final StoreServiceClient storeClient; // prod(prod.store.co.kr), dev(dev.store.co.kr)
	private final MockStoreClient mockClient;

	/**
	 * 예약 생성 API 테스트를 위한 임시 데이터
	 * Feature Flag
	 * - false: MockClient 사용 (dev)
	 * - true: 실제 StoreClient 사용 (prod)
	 **/
	@Value("${feature-flag.store-service}")
	boolean flag;

	@Override
	public TimeslotResult getTimeslot(UUID timeslotId) {
		log.info("🎯 getTimeslot 호출 - flag: {}", flag);
		if (flag) {
			SearchTimeslotResponse response = storeClient.getTimeslot(timeslotId);
			return new TimeslotResult(
				response.timeslotId(),
				response.storeId(),
				response.date(),
				response.status()
			);
		}
		SearchTimeslotResponse response = mockClient.getTimeslot(timeslotId);
		return new TimeslotResult(
			response.timeslotId(),
			response.storeId(),
			response.date(),
			response.status()
		);
	}
}
