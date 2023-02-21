# doChat : 채팅 웹사이트 구현

* 네트워크를 통해 서버로부터 데이터를 가져오기 위한 통신 방식 2가지 : 
  (ref: https://blog.naver.com/jamsuham75/222337979985)
  
<h2>1) HTTP 통신</h2>
> 클라이언트 요청이 있을 때에만 서버가 응답하여 해당 정보를 전송하고 곧바로 연결을 종료하는 방식.<br/>
> 단방향적 통신으로, 서버가 클라이언트 쪽으로 요청을 보낼 수 없음.<br/>
> 주로 콘텐츠 위주의 데이터를 사용할 때 유용 (인터넷으로 주소를 검색하여 연결)<br/>
    
<h3>(1) HTTP Poling</h3>
    
![image](https://user-images.githubusercontent.com/108982584/220249260-12d90d8d-8aca-44db-a6de-cfbbf46aac69.png)
    
     * 작동방식 : 클라이언트가 평범한 HTTP Request를 서버로 계속 요청해 이벤트 내용을 전달받는 방식.
     * 장점 : 가장 쉬운 방법
     * 단점 : 클라이언트가 지속적으로 Request를 요청하기 때문에 클라이언트의 수가 많아지면 서버의 부담이 증가.
            : HTTP Request Connection을 맺고 끊는 것 자체가 부답이 맣은 방식이고, 클라이언트에서 실시간 정도의 빠른 응답을 기대하기 어려움!


<h3>(2) HTTP Long Poling</h3>
    
![image](https://user-images.githubusercontent.com/108982584/220250725-01a970b2-e57b-4da5-bbc5-d22c488378c0.png)

     * 작동방식 : 클라이언트에서 서버로 일단 HTTP Requet를 요청
               : 그 상태로 계속 대기하다가 서버에서 클라이언트로 전닳할 이벤트가 있다면 그 순간 Response 메시지를 전달하며 연결 종료
               : 곧 클라이언트가 다시 HTTP Request를 요청해 다음 이벤트를 기다리는 방식
     * 장점 : HTTP Poling 방식보다 서버의 부담이 줄어들 수 있음
     * 단점 : 클라이언트로 보내는 이벤트들의 시간간격이 좁다면 Poling과 별 차이가 없음.
            : 다수의 클라이언트에게 동시에 이벤트가 발생될 경우 서버의 부담이 급증.



<h3>(3) HTTP Streaming</h3>
    
![image](https://user-images.githubusercontent.com/108982584/220254015-4f86f0c8-8b9b-49c9-91fd-9a38166928a0.png)
 
      * 작동방식 : 클라이언트 -> 서버로 HTTP Request를 요청
                : 클라이언트로 이벤트를 전달할 때, 해당 요청을 해제하지 않고 핋요한 메시지만 보냄(Flush)
      * 장점 : Long Poling과 비교하여 서버에 메세지를 보내지 않고도 다시 HTTP 연결을 하지 않아도 되어 부담이 줄어듬.

<h2>2) Socket 통신 </h2>

<h3>(1) Socket 통신이란? </h3> (ref : https://recipes4dev.tistory.com/153)

![image](https://user-images.githubusercontent.com/108982584/220257796-23fd23ab-fbbb-4809-b78b-9ceab1a1c9fb.png)

      * Network Socket : 네트워크 환경에 연결할 수 있게 만들어진 연결부
      * 서버와 클라이언트가 특정 port를 통해 연결하여 실시간 양방향 통신을 하는 방식.
      * HTTP 통신과 달리 Socket 통신은 서버 역시 클라이언트로 요청을 보낼 수 있고, 실시간으로 연결이 유지됨.
      * 실시간 스트리밍 혹은 채팅과 같은 즉각적인 양방향 정보 교환이 필요한 경우 많이 사용.

<h4> 1. TCP/IP 소켓 프로그래밍 </H4>
  > TCP소켓, TCP/IP 소켓 : OSI 7 Layer(Open System Interconnection 7 Layer)의 네번째 계층인 TCP(Transport Control Protocol) 상에서 동작하는 소켓<br/>
  > 데이터를 주고받기 위해서는 먼저 소켓의 연결 과정이 선행되어야 함 <br/>
  > 클라이언트 소켓: 연결 요청 <br/>
  > 서버 소켓 : 수신 <br/>
  

<h4> 1-1. 클라이언트 소켓과 서버 소켓 </h4>

![image](https://user-images.githubusercontent.com/108982584/220259303-5b72ec22-c2be-42ff-b2d1-5344f6f1c91c.png)

      * 두 개의 시스템 (또는 프로세스)이 소켓을 통해서 Connection 하기 위해서는 최초 어느 한 곳에서 대상이 되는 곳으로 연결을 요청해야함.
      * IP주소와 포트번호로 식별되는 대상에게 자신이 데이터 송수신을 위한 네트워크 연결을 수립할 의사가 있을을 알리는 것.




<h3>(2)  Web Socket ? </h3>
  > 기존의 단방향 HTTP 프로토콜과 호환되어 양방향 통신을 제공하기 위해 개발된 프로토콜.<br/>
  > 일반 Socket 통신과 달리 HTTP Port를 사용하므로 방화벽에 제약이 없으며 통상 WebSocket으로 불린다.<br/>
  > 접속까지는 HTTP 프로토콜을 이용. 그 이후 통신은 자체적인 Web Socket 프로토콜로 통신.<br/>


<h2> 개발노트 </h2>
<h3>1차 : localhost에서 구현 (2023-02-20)</h3> (ref : https://dev-gorany.tistory.com/212)
  ! 문제점 : 모든 클라이언트의 브라우저에서 WebSocket을 지원한다는 보장이 없다.
            또한, Server/Client 중간에 위치한 Proxy가 Upgrade헤더를 해석하지 못해 서버에 전달하지 못할 수 있다. 마지막으로
            Server/Client 중간에 위치한 Proxy가 유휴 상태에서 도중에 Connection 종료시킬 수도 있다.
