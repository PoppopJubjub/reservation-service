package com.popjub.reservationservice.exception;

import org.springframework.http.HttpStatus;

import com.popjub.common.exception.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode implements BaseErrorCode {

	NOT_FOUND_RESERVATION("예약을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	DUPLICATE_RESERVATION("이미 해당 날짜에 팝업스토어 예약이 존재합니다.", HttpStatus.CONFLICT),
	TIMESLOT_NOT_AVAILABLE("해당 타임슬롯은 현재 예약 가능 상태가 아닙니다.", HttpStatus.BAD_REQUEST),
	CANNOT_CANCEL_RESERVATION("취소할 수 없는 예약입니다. 본인의 예약이며 '예약 확정' 상태인 경우만 취소 가능합니다.", HttpStatus.BAD_REQUEST),
	ALREADY_CANCELLED("이미 취소된 예약입니다.", HttpStatus.BAD_REQUEST),
	FORBIDDEN_RESERVATION_ACCESS("본인의 예약만 조회할 수 있습니다.", HttpStatus.FORBIDDEN),
	QR_GENERATION_FAILED("QR 코드 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_QR_CODE("유효하지 않은 QR 코드입니다.", HttpStatus.BAD_REQUEST),
	INVALID_RESERVATION_ID("유효하지 않은 예약 ID입니다.", HttpStatus.BAD_REQUEST),
	INVALID_USER_ID("유효하지 않은 사용자 ID입니다.", HttpStatus.BAD_REQUEST),
	INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_SHOW_ALREADY_EXISTS("이미 노쇼 처리된 예약입니다.", HttpStatus.CONFLICT),
	CHECKIN_NOT_AVAILABLE("체크인 가능한 상태가 아닙니다.", HttpStatus.BAD_REQUEST),
	INVALID_CHECK_IN_DATE("체크인 가능한 날짜가 아닙니다.", HttpStatus.BAD_REQUEST),
	CHECKIN_VALIDATION_FAILED("체크인 검증에 실패했습니다.", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;
}
