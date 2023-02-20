package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.chat.handler.ChatHandler;

import lombok.RequiredArgsConstructor;

/*
 * WebSocket에 접속하기 위한 EndPoint 설정 : /chat
 * Domain이 다른 서버에서도 접속 가능하도록 CORS:setAllowedOrigins("*"); 를 추가.
 * 클라이언트가 ws://localhost:8888/chat으로 커넥션을 연결하고 메세지 통신을 할 수 있음
 *    
 *  EndPoint : API가 서버에서 자원(resource)에 접근할 수 있도록 하는 URL
 *  > 메서드 같은 URL들에 대해서 다른 요청을 할 수 있도록 구별하게 해주는 항목.
 *  > 각각 get, put, delete 메서드에 따라 다른 요청이 가능함.
 *  
 * CORS( Cross-Origin Resource Sharing )  : 교차 출처 리소스 공유
 *  > 추가 HTTP 헤더를 사용해 한 출처에서 실행중인 웹 어플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을 부여하도록 브라우저에 알려주는 체제.
 *  >  웹 어플리케이션은 리소스가 자신의 출처 (domain, protocol, port)와 다를 때 교차 출처 HTTP 요청을 실행
 *  > 이에 대한 응답으로 서버는 Access - Control - Allow - Origin 헤더를 다시 보냄.

 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket // WebSocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {

	private final ChatHandler chatHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
	}
}
