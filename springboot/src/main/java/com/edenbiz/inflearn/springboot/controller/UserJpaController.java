package com.edenbiz.inflearn.springboot.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edenbiz.inflearn.springboot.bean.Post;
import com.edenbiz.inflearn.springboot.bean.User;
import com.edenbiz.inflearn.springboot.bean.UserListDTO;
import com.edenbiz.inflearn.springboot.exception.UserNotFoundException;
import com.edenbiz.inflearn.springboot.repository.PostRepository;
import com.edenbiz.inflearn.springboot.repository.UserRepository;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	
	private UserRepository userRepository;
	private PostRepository postRepository;

	public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	// JPA를 사용하여 모든 사용자 조회
	@GetMapping("/users")
	public ResponseEntity retrieveAllUsers() {
		return ResponseEntity.ok(UserListDTO.builder()
				.usersNum(userRepository.count())
				.userList(userRepository.findAll())
				.build());
	}
	
	// JPA를 사용하여 특정 사용자 조회
	@GetMapping("/users/{id}")
	public ResponseEntity retrieveUsersById(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		// user 변수는 Optional객체이기 때문에 get()메소드로 user 데이터 값 추출
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());  
		entityModel.add(link.withRel("all-users"));
		
		return ResponseEntity.ok(entityModel);
	}
	
	// JPA를 사용하여 특정 사용자 삭제
	@DeleteMapping("/users/{id}")
	public void deleteLUserById(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	// JPA를 사용하여 사용자 생성
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		// URI 생성 과정
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	// JPA를 사용하여 특정 사용자의 포스트 조회
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsById(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		return user.get().getPosts();
	}
	
	// JPA를 사용하여 게시글 작성
	@PostMapping("/users/{id}/posts")
	public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}

}
