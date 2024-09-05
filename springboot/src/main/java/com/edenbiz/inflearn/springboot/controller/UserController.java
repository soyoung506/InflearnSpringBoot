package com.edenbiz.inflearn.springboot.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edenbiz.inflearn.springboot.bean.User;
import com.edenbiz.inflearn.springboot.dao.UserDaoService;
import com.edenbiz.inflearn.springboot.exception.UserNotFoundException;

@RestController
public class UserController {
	private UserDaoService service;

	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		return user;
	}
	
	// CREATED
	// input - details of user
	// output - CREATED & Return the created URI
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {  // Valid 어노테이션을 통해 요청받은 User 객체의 유효성을 확인
		User savedUser = service.save(user);
		
		// URI 생성 과정
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()  // 현재 request 즉, 현재 URI에서
				.path("/{id}")  // id 파라미터를 추가할건데
				.buildAndExpand(savedUser.getId())  // id 파라미터에 새로 추가한 사용자의 id값을 추가
				.toUri();  // URI 생성
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		User deltedUser = service.deleteById(id);
		
		if (deltedUser == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		return ResponseEntity.noContent().build();

	}
}
