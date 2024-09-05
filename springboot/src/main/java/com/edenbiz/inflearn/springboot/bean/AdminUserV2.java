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
@JsonFilter("UserInfoV2")
// 기존에 존재하는 AdminUser DTO를 상속받아 version2 생성
public class AdminUserV2 extends AdminUser{
	// AdminUser가 가진 모든 속성을 포함하고 grade 속성 추가
	private String grade;
}
