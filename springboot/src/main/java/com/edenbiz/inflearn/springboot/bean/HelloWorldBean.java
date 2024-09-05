package com.edenbiz.inflearn.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  // Setter, Getter, toString, ... 자동 생성
@AllArgsConstructor  // 현재 가지고 있는 모든 프로퍼티를 포함한 생성자 추가
public class HelloWorldBean {
	private String message;

	// AllArgsConstructor 어노테이션과의 충돌로 주석 처리
//	public HelloWorldBean(String message) {
//		this.message = message;
//	}
}
