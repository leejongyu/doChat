package com.chat.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chat.domain.Users;
import com.chat.dto.UserCreateDto;
import com.chat.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * User를 관리하기 위한 controller
 * 
 * @author 이존규
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * sign up 버튼을 누르면 회원가입 페이지로 이동 생성자 : 이존규
     */
    @GetMapping("/signUp")
    public void signUp() {
        log.info("wac - sign Up");
    }

    /**
     * @param : web page에서 입력받은 정보를 dto로 변환하여 입력받음
     * @return : main page로 redirect 생성자 : 이존규
     */
    @PostMapping("/signUp")
    public String create(UserCreateDto dto) {

    	Users user = userService.createUser(dto);
        
        log.info("user role = {}", user.getRoles());
        
        return "redirect:/login";
    }

    
    

}
