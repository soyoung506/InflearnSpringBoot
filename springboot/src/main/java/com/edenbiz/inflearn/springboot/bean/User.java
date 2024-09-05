package com.edenbiz.inflearn.springboot.bean;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"}) // Json에 노출하지 않을 변수 설정
public class User {
	private Integer id;
	
	// name 변수값이 최소 2 이상이어야 함을 설정 (Validation 유효값 설정)
	@Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
	private String name;
	
	// joinDate 변수값이 과거값이어야 함을 설정 (Validation 유효값 설정)
	@Past(message = "등록일은 미래 날짜를 입력할 수 없습니다.")
	private Date joinDate;
	
//	@JsonIgnore
	private String password;
	
//	@JsonIgnore
	private String ssn;
	
	// 빈 생성자가 없을 경우 cannot deserialize from object value (no delegate- or property-based creator) 에러 발생
	// jackson library가 빈 생성자가 없는 모델을 생성하는 방법을 모르기 때문
	// 빈 생성자를 생성하거나 Lombok의 @NoArgsConstructor 어노테이션 설정
	public User() {
		
	}
}
