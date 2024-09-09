package com.edenbiz.inflearn.springboot.bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)  // User 데이터 로드 시 Post 정보를 즉시 로딩 하지 않고 getPosts()와 같은 메소드 사용 시에만 로딩시키는 지연로딩(LAZY)
	@JsonIgnore  // post 데이터 로드 시 User 정보는 필터링
	private User user;
}
