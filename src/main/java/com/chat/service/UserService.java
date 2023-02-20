package com.chat.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.domain.Users;
import com.chat.dto.UserCreateDto;
import com.chat.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service // 스프링 컨택스트에 service 컴포넌트로 등록.
public class UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	public Users createUser(UserCreateDto dto) {
		log.info("User Create(userCreate Dto = {})", dto);

		dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword())); // 입력받은 비밀번호를 암호화
		Users entity = userRepository.save(dto.toEntity());

		log.info("entity = {}", entity);

		return entity;
	}
	
    @Transactional(readOnly = true)
    public Users read(String userName) {
        log.info("User read(userId = {})", userName);
        
        return userRepository.findByUserName(userName).get();
    }
}
