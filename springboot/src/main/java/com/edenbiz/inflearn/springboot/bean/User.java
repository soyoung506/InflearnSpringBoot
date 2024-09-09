package com.edenbiz.inflearn.springboot.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"}) // Json에 노출하지 않을 변수 설정
@Entity  // 데이터베이스의 릴레이션을 생성/연결하는 어노테이션
@Table(name = "users")  // 데이터베이스 릴레이션의 이름 설정
public class User {
	@Id  // 해당 릴레이션의 기본키임을 정의
	@GeneratedValue  // 해당 기본키는 자동으로 생성됨을 정의
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
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	// 빈 생성자가 없을 경우 cannot deserialize from object value (no delegate- or property-based creator) 에러 발생
	// jackson library가 빈 생성자가 없는 모델을 생성하는 방법을 모르기 때문
	// 빈 생성자를 생성하거나 Lombok의 @NoArgsConstructor 어노테이션 설정
	public User() {
		
	}

	public User(Integer id, @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.") String name,
			@Past(message = "등록일은 미래 날짜를 입력할 수 없습니다.") Date joinDate, String password, String ssn) {
		this.id = id;
		this.name = name;
		this.joinDate = joinDate;
		this.password = password;
		this.ssn = ssn;
	}
	
	
}
