package com.chat.dto;

import com.chat.domain.UserRole;
import com.chat.domain.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserCreateDto {
	private String userName; // 로그인에 사용할 이름
	private String userPassword; // 로그인에 사용할 비밀번호
	
	public Users toEntity() {

		if (userName.equals("admin")) {
			return Users.builder().userName(userName).userPassword(userPassword).build().addRole(UserRole.USER).addRole(UserRole.ADMIN);
		} else {
			return Users.builder().userName(userName).userPassword(userPassword).build().addRole(UserRole.USER); 
		}
 
	}
	
}
