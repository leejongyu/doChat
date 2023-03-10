package com.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.domain.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	List<Users> findByOrderByUserIdDesc();

	// 사용자 로그인 아이디(UserName)가 일치하는 사용자 정보 검색
	@EntityGraph(attributePaths = "roles")
	Optional<Users> findByUserName(String userName);


}
