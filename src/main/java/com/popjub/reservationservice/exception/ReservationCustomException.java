package com.popjub.reservationservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ReservationCustomException extends RuntimeException {

	private final ReservationErrorCode errorCode;
	private final String message;
	private final HttpStatus status;

	public ReservationCustomException(ReservationErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
	}
}
