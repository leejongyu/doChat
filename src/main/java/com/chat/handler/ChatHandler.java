package com.chat.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> list = new ArrayList<>();

	/*
	 * Payload : 전송되는 데이터. 데이터를 전송할 때, Header와 META 데이터, 에러 체크 비트 등과 같은 다양한 요소들을 함께
	 * 보내서 데이터 전송 효율과 안정성을 높임.
	 * 이 때, 보내고자 하는 데이터 자체를 의미하는것이 '페이로드'
	 * 
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("patload = {}", payload);

		for (WebSocketSession s : list) {
			s.sendMessage(message);
		}
	}

	// client가 접속 시 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		list.add(session);

		log.info(session + "클라이언트 접속");
	}

	// client 접속 햐제 시 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		log.info(session + "클라이언트 접속 해제");
		list.remove(session);

	}

}
