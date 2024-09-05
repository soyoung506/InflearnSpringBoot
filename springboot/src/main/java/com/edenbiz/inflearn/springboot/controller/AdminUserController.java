package com.edenbiz.inflearn.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edenbiz.inflearn.springboot.bean.AdminUser;
import com.edenbiz.inflearn.springboot.bean.AdminUserV2;
import com.edenbiz.inflearn.springboot.bean.User;
import com.edenbiz.inflearn.springboot.dao.UserDaoService;
import com.edenbiz.inflearn.springboot.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
	private UserDaoService service;

	public AdminUserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers4Admin() {
		List<User> users = service.findAll();
		
		List<AdminUser> adminUsers = new ArrayList<>();
		AdminUser adminUser = null;
		
		for (User user : users) {
			adminUser = new AdminUser();
			BeanUtils.copyProperties(user, adminUser);
			adminUsers.add(adminUser);
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
		mapping.setFilters(filters);
		
		return mapping;
	}

//	@GetMapping("/v1/users/{id}")
//	@GetMapping(value = "/users/{id}", params = "version=1")
//	@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
	// AdminUser 객체 모두 반환하는 것이 아닌 JsonFilter에 따라 필터링된 AdminUser 객체를 반환하기 위해 MappingJacksonValue 클래스 형태로 반환
	// MappingJacksonValue 클래스는 java 객체를 JSON 데이터로 변환/
	public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
		User user = service.findOne(id);
		
		AdminUser adminUser = new AdminUser();
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}else {
			// 필드 모두 setter로 복붙할 수 있지만 필드가 많을 경우 코드가 많아지기 때문에 BeanUtils에서 제공하는 속성 복사 메소드 활용
//			adminUser.setName(user.getName());
			BeanUtils.copyProperties(user, adminUser);  // user -> adminUser 복사
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	// URI를 활용하여 version2 사용자 조회
//	@GetMapping("/v2/users/{id}")
	// 파라미터를 활용하여 version2 사용자 조회
//	@GetMapping(value = "/users/{id}", params = "version=2")
	// 헤더를 활용하여 version2 사용자 조회
//	@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
	// mime-type을 활용하여 version2 사용자 조회
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
		User user = service.findOne(id);
		
		AdminUserV2 adminUser = new AdminUserV2();
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}else {
			BeanUtils.copyProperties(user, adminUser);
			// User DTO에는 grade 속성이 없기 때문에 setter를 통해 따로 추가
			adminUser.setGrade("VIP");
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
}
