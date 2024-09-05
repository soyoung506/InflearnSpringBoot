package com.edenbiz.inflearn.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 원하는 예외처리 코드 설정
@ResponseStatus(HttpStatus.NOT_FOUND)  // code 404
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
