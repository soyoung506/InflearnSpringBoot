package com.edenbiz.inflearn.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		
		// yml 파일에서 설정한 user 정보와 다르게, 계속해서 새로운 user 정보를 추가할 수 있음
		UserDetails newUser = User.withUsername("user")
				.password(passwordEncoder().encode("password!"))  // encode 메소드를 통해 비밀번호 암호화
				.authorities("read")  // 권한: 읽기
				.build();
		
		userDetailsManager.createUser(newUser);
		return userDetailsManager;
	}
	
	// 비밀번호 암호화를 위해 암호화 메소드 생성
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
