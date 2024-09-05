package com.edenbiz.inflearn.springboot;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

// 임시적으로 DB 사용하지 않기
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SpringbootApplication {

	public static void main(String[] args) {
//		ApplicationContext ac = 
		SpringApplication.run(SpringbootApplication.class, args);
		
		// 저장된 bean 호출
//		String[] allBeanNames = ac.getBeanDefinitionNames();
//		for (String beanName : allBeanNames) {
//			System.out.println(beanName);
//		}
	}
	
	// 외부 라이브러리인 LocaleResolver를 사용하기 위해 Bean 어노테이션을 사용해서 객체 생성
	@Bean
	public LocaleResolver localeResolver() {
		// LocaleResolver의 한 종류로 SessionLocaleResolver를 사용하여 서버의 Session에 Locale 정보 저장
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

}
