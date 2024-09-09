package com.edenbiz.inflearn.springboot.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDTO {
	
	private Long usersNum;
	private List<User> userList;
	
	@Builder
	public UserListDTO(Long usersNum, List<User> userList) {
		this.usersNum = usersNum;
		this.userList = userList;
	}

}
