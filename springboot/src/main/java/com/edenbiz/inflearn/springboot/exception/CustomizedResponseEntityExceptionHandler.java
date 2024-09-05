package com.edenbiz.inflearn.springboot.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Component 어노테이션의 특수한 케이스로, 스프링 부트 애플리케이션에서 전역적으로 예외를 핸들링할 수 있게 해주는 어노테이션
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	// 모든 Exception 예외처리
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  // 사용자에게 모든 trace 정보를 제공하지 않기 위해 description 값을 false로 설정
		
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);  // 시간, 메시지, 상세설명을 포함한 예외처리 객체와 에러코드 500 전달
	}
	
	// UserNotFoundException 예외처리
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);  // 에러코드 404 전달
	}

	// ResponseEntityExceptionHandler 부모 클래스에서 지원하는 handleMethodArgumentNotValid(유효성 예외) 예외처리 오버라이딩
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().toString());
		
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);  // 에러코드 400 전달
	}
	
	
}
