package com.edenbiz.inflearn.springboot.bean;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")  // 사용자 지정으로 만든 필터명 설정
public class AdminUser {
	private Integer id;
	
	// name 변수값이 최소 2 이상이어야 함을 설정 (Validation 유효값 설정)
	@Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
	private String name;
	
	// joinDate 변수값이 과거값이어야 함을 설정 (Validation 유효값 설정)
	@Past(message = "등록일은 미래 날짜를 입력할 수 없습니다.")
	private Date joinDate;
	
	private String password;
	private String ssn;
}
