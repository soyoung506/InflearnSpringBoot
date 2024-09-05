package com.edenbiz.inflearn.springboot.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.edenbiz.inflearn.springboot.bean.HelloWorldBean;

@RestController
public class HelloWorldController {
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	// GET
	// URI - /hello-world
	// @RequestMapping(method=RequestMethod.GET, path="/hello-world")
	@GetMapping(path = "hello-world")
	public String helloworld() {
		// 텍스트를 직접 전달/리턴
		return "Hello World";
	}
	
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean helloworldBean() {
		// Object(Bean) 타입으로 전달/리턴 -> 스프링부트에서 자동으로 response body로 변환 -> json 형태값으로 전달/리턴(json이 기본포맷)
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path = "hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloworldBeanPathVariable(@PathVariable String name) {
		// PathVariable 어노테이션을 통해 URI에서 전달받은 하나의 파라미터를 String.format 메소드로 출력
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	@GetMapping(path = "hello-world-internationalized")
	public String helloworldInternalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {  // Header에 Accept-Language가 있다면 Locale 값을 파라미터로 추출
		// 파라미터로 받은 Locale 정보에 따라 messageSource가 지정한 적잘한 message.properties의 greeting.message 출력
		return messageSource.getMessage("greeting.message",null, locale);
	}

}
