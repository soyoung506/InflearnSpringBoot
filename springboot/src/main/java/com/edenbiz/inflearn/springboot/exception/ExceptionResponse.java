package com.edenbiz.inflearn.springboot.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  // 빈 생성자 생성
public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String detatils;
}
