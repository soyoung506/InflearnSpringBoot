package com.edenbiz.inflearn.springboot.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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
// @Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러입니다.")
public class UserController {
	private UserDaoService service;

	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	// 특정 경로에 대한 작업 (HTTP 메소드 설명)
// 	@Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보 조회를 합니다.")
	// API 작업 처리에 대한 응답코드 설명
//	@ApiResponses({
//					@ApiResponse(responseCode = "200", description = "OK"),
//					@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//					@ApiResponse(responseCode = "404", description = "USER NOT FOUND"),
//					@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//		}
//	)
	@GetMapping("/users/{id}")
	// EntityModel은 클라이언트가 해당 리소스와 연관된 다른 리소스로 쉽게 이동할 수 있도록 링크정보를 제공하는 데 사용
	// ResponseEntity는 HTTP 응답의 상세한 제어(상태코드, 헤더 등)이 필요할 때 사용
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		// EntityModel은 생성자를 제공하지 않고 다른 객체를 통해 of 메소드로 생성
		EntityModel<User> entityModel = EntityModel.of(user);
		
		// EntityModel 객체에 추가할 링크 생성
		// 현재 클래스(UserController)에서 retrieveAllUsers 메소드를 실행할(methodOn) 링크 생성(linkTo)
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());  
		// EntityModel 객체에 링크 추가
		// 생성한 링크(link)를 withRel 메소드로 링크 이름(all-users)과 함께 추가
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
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
