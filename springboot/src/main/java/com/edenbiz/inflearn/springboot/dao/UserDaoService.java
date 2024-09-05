package com.edenbiz.inflearn.springboot.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.edenbiz.inflearn.springboot.bean.User;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount = 3;
	
	static {
		users.add(new User(1, "Soyoung", new Date(), "something", "111111-1111111"));
		users.add(new User(2, "jihee", new Date(), "heehee", "222222-2222222"));
		users.add(new User(3, "sumin", new Date(), "minmin", "333333-3333333"));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		
		if (user.getJoinDate() == null) {
			user.setJoinDate(new Date());
		}
		
		users.add(user);
		
		return user;
	}
	
	public User findOne(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		
		return null;
	}
	
	public User deleteById(int id) {
		// Iterator: 반복자, 배열 따위의 자료구조 내부 요소를 순회하는 객체
		// List 객체인 users 변수로부터 순회객체로 변환/생성
		Iterator<User> iterator = users.iterator();
		
		while (iterator.hasNext()) {
			User user = iterator.next();
			
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		
		return null;
	}
	
}
