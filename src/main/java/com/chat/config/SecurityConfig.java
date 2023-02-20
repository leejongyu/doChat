package com.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration 
public class SecurityConfig {

    @Bean 
    public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
        
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        // 로그인/ 로그아웃 관련 설정
        http.formLogin(Customizer.withDefaults()) // Spring security에서 제공하는 기본 로그인 폼 사용
        .logout() // 로그아웃 관련 설정
        .logoutSuccessUrl("/login"); // 로그아웃 성공 후 이동할 url
        
        
        return http.build();
    }
    
}
