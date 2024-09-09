package com.edenbiz.inflearn.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
	
	// spring security에서 무시할 URI 설정
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()  // WebFlux 또는 react에서 사용되는 표기법 (체이닝 코드)
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}
	
	// HTTP Get메소드는 리소스를 수정하는 것이 아니라 조회할 뿐이기 때문에 별도의 Spring Security 작업이 필요하지 않지만 그 외의 HTTP 메소드는 SecurityFilterChain 작업이 필요함
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		// csrf(cross site request forgery attack): 크로스 사이트 요청 위조 공격, 인증된 사용자가 웹 어플리케이션에서 특정 요청을 보내도록 유도하는 공격 행위
		// 임시 허용 작업
		http.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
