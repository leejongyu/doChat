# doChat
채팅 웹사이트 구현

* 네트워크를 통해 서버로부터 데이터를 가져오기 위한 통신 방식 2가지 : 

1) HTTP 통신 ?
  > 클라이언트 요청이 있을 때에만 서버가 응답하여 해당 정보를 전송하고 곧바로 연결을 종료하는 방식.
  > 단방향적 통신으로, 서버가 클라이언트 쪽으로 요청을 보낼 수 없음.

2) Socket 통신 ?
  > 서버와 클라이언트가 특정 port를 통해 연결하여 실시간 양방향 통신을 하는 방식.
  > HTTP 통신과 달리 Socket 통신은 서버 역시 클라이언트로 요청을 보낼 수 있고, 실시간으로 연결이 유지됨.
  > 실시간 스트리밍 혹은 채팅과 같은 즉각적인 양방향 정보 교환이 필요한 경우 많이 사용.

Web Socket ? 
  > 기존의 단방향 HTTP 프로토콜과 호환되어 양방향 통신을 제공하기 위해 개발된 프로토콜.
  > 일반 Socket 통신과 달리 HTTP Port를 사용하므로 방화벽에 제약이 없으며 통상 WebSocket으로 불린다.


1차 : localhost에서 구현 (2023-02-20) (ref : https://dev-gorany.tistory.com/212)
  > 문제점 : 
            1) 모든 클라이언트의 브라우저에서 WebSocket을 지원한다는 보장이 없다.
            2) 또한, Server/Client 중간에 위치한 Proxy가 Upgrade헤더를 해석하지 못해 서버에 전달하지 못할 수 있다. 마지막으로
            3) Server/Client 중간에 위치한 Proxy가 유휴 상태에서 도중에 Connection 종료시킬 수도 있다.
