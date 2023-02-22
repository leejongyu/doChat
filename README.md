# doChat : 채팅 웹사이트 구현

* 네트워크를 통해 서버로부터 데이터를 가져오기 위한 통신 방식 2가지 : <br/>
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

<h3> 1. TCP/IP 소켓 프로그래밍 </h3>
> TCP소켓, TCP/IP 소켓 : OSI 7 Layer(Open System Interconnection 7 Layer)의 네번째 계층인 TCP(Transport Control Protocol) 상에서 동작하는 소켓<br/>
> 데이터를 주고받기 위해서는 먼저 소켓의 연결 과정이 선행되어야 함 <br/>
> 클라이언트 소켓: 연결 요청 <br/>
> 서버 소켓 : 수신 <br/>
  

<h4> 1-1. 클라이언트 소켓과 서버 소켓 </h4>

![image](https://user-images.githubusercontent.com/108982584/220259303-5b72ec22-c2be-42ff-b2d1-5344f6f1c91c.png)

      * 두 개의 시스템 (또는 프로세스)이 소켓을 통해서 Connection 하기 위해서는 최초 어느 한 곳에서 대상이 되는 곳으로 연결을 요청해야함.
      * IP주소와 포트번호로 식별되는 대상에게 자신이 데이터 송수신을 위한 네트워크 연결을 수립할 의사가 있을을 알리는 것.

![image](https://user-images.githubusercontent.com/108982584/220262844-b7a6cc69-a0ce-4683-a120-7972e051cdfd.png)

      * 단, A에서 연결을 시도한다고 무조건 연결되어 데이터를 주고 받을 수는 없음
      * 대상이 되는 곳에서 요청을 받아들일 준비가 되어있지 않으면 해당 요청은 무시되고 연결은 만들어지지 않음.
      
![image](https://user-images.githubusercontent.com/108982584/220263895-a33140e6-bf8f-4939-993b-a500fa5fc367.png)

      * 요청을 받아들이는 곳에서는 어떤 연결 요청(대부분 포트 번호)을 받아들일 것인지 미리 등록하여, 요청이 수신되었을 때 해당 요청을 처리할 수 있도록 준비해야 함
      * 이 과정에서 두 개의 시스템(또는 프로세스)이 소켓을 통해 데이터 통신을 위한 연결(Connection)을 만들기 위해서는, 연결 요청을 보내는지 또는 요청을 받아들이는지에 따라 소켓의 역할이 나뉘게 됨
       > 연결 요청을 보내는 소켓 : 클라이언트 소켓 (Client Socket)
       > 연결 요청을 받아들이는 소켓 : 서버 소켓 (Server Socket)

![image](https://user-images.githubusercontent.com/108982584/220265106-aacc2929-3f0e-4532-8a83-711319d0b184.png)

      
      * 클라이언트 소켓과 서버 소켓은 역할과 구현 절차 구분을 위해 다르게 부르는 것.
       > 역할에 따라 처리되는 효율. 즉, 호출되는 API함수의 종류와 순서들이 다를 뿐 동일한 소켓(Socket)
       
      * 소켓 연결이 되면 클라이언트소켓과 서버소켓이 직접 데이터를 주고받는 것이 아님.
       > 서버 소켓은 클라이언트 소켓의 연결 요청을 받아들이는 역할만 수행.
       > 직접적인 데이터 송수신은 서버소켓의 연결 요청 수락의 결과로 생성되는 새로운 소켓을 통해 처리.


<h4> 1-2. 소켓 API 실행 흐름 </h4>

![image](https://user-images.githubusercontent.com/108982584/220265968-1bad2a18-600a-4a23-9da9-cdf5ef73b8dd.png)

      (1) Client Socket
        1. socket() : 소켓을 생성
          * 소켓 통신을 위해 가장 먼저 해야 할 일
          * 이 때, 소켓의 종류를 지정할 수 있음 (TCP 소켓 : Stream 타입/ UDP 소켓 : Datagram 타입 등)
          * 최초 소켓이 생성될 때에는 "연결 대상"에 대한 어떠한 정보도 담고있지 않음. (껍데기 소켓만 생성)
          * 연결 대상(IP PORT)을 지정하고 연결 요청을 전달하기 위해서는, 여기서 생성한 소켓을 사용하여 connect() API를 호출해야 함.
            
        2. connect() : 서버측에 연결을 요청
          * connect() API는 "IP주소"와 "포트 번호"로 식별되는 대상(Target)으로 연결 요청을 보냄
          * connect() API는 블럭(block) 방식으로 동작.
           > 연결 요청에 대한 결과 (성공/ 거절/ 시간 초과 등)가 결정되기 전에는 connect()의 실행이 끝나지 않음
           > connect() API가 실행되자마자 실행 결과와 관계 없이 무조건 바로 리턴 될 것이라고 가정해선 안됨.
          * connect() API 호출이 성공하면, send() / recv() API를 통해 데이터를 주고 받음
            
        3. send() / recv() : 서버측에서 연결이 받아들여지면 데이터를 주고 받음
          * 연결된 소켓을 통해 데이터를 보낼 때는 send(), 받을 때는 recv() API를 사용
          * 두 API 모두 (block)방식으로 동작
           > 결과 (성공/ 거절/ 시간 초과 등)가 결정되기 전에는 API가 리턴되지 않음
           > 특히, recv()의 경우 데이터가 수신되거나, 에러가 발생하기 전에는 실행이 종료되지 않기 때문에, 데이터 수신 작업을 생각만큼 단순하게 처리하기 쉽지 않음
            - send()의 경우 데이터를 보내는 주체가 자기 자신이기 때문에, 얼마만큼의 데이터를 보낼 것인지, 언제 보낼 것인지 알 수 있음.
            - 그러나 recv()의 경우 통신 대상이 언제, 어떤 데이터를 보낼 것인지를 특정할 수 없기 때문에 recv() API가 한번 실행되면 언제 끝날지 모르는 상태가 되는 것.
             > recv() API는 별도의 스레드에서 실행. 
                (소켓의 생성과 연결이 완료된 후, 새로운 스레드를 하나 만든 다음 그 곳에서 recv()를 실행하고 데이터가 수신 되는 것을 기다림)
              
        4. close() : 모든 처리가 완료되면 소켓을 닫음
          * 데이터 송수신이 필요 없게 되면 close() API로 소켓을 닫음.
          * 닫힌 소켓은 유효한 소켓이 아니기 때문에, 해당 소켓을 사용하여 데이터를 송수신 할 수 없음.
          * 또 다시 생성 하려면 socket()과 connect() 과정을 통해서 send/recv 준비가 되어야 함.
       
      (2) Server Socket
        1. socket() : 소켓을 생성
        
        2. bind() : 서버가 사용할 IP주소와 포트번호를 생성한 소켓을 연결
          * bind() API에 사용되는 인자는 소켓과 포트번호(또는 IP주소+포트번호)
          * 일반적으로 서버 소켓은 고정된 포트번호를 사용하고, 그 포트 번호로 클라이언트의 연결요청을 받아들임.
          * 운영체제가 특정 포트 번호를 서버 소켓이 사용하도록 만들기 위해 소켓과 포트 번호를 결합하는 API
           > 운영체제는 소켓이 사용하는 포트 번호가 다른 소켓의 포트 번호와 중복되지 않도록 내부적으로 포트 번호와 소켓 연결 정보를 관리.
           > bind() API는 해당 소켓이 지정된 포트 번호를 사용할 것이라는 것을 운영체제에 요청하는 API
           > 만약 지정된 포트 번호를 다른 소켓이 사용하고 있다면 bind() API는 에러를 return
           
        3. listen() : 클라이언트로부터 연결 요청이 수신되는지 확인
          * isten() API는 서버 소켓(Server Socket)에 바인딩된 포트 번호로 클라이언트의 연결 요청이 있는지 확인하며 대기
          * 클라이언트에서 호출된 connect() API에 의해 연결 요청이 수신되는지 대기하다가, 요청이 수신되면, 그 때 대기 상태를 종료하고 리턴
          * listen() API가 대기 상태에서 빠져나오는 경우
            (1) 클라이언트 요청이 수신
            (2) 에러가 발생(소켓 close() 포함)
          * listen() API가 성공한 경우라도, 리턴 값에 클라이언트의 요청에 대한 정보는 들어 있지 않음
           > isten()의 리턴 값으로 판단할 수 있는 것 
            (1) 클라이언트 연결 요청이 수신되었는지(SUCCESS)
            (2) 그렇지 않고 에러가 발생했는지(FAIL)
          * 클라이언트 연결 요청에 대한 정보는 시스템 내부적으로 관리되는 큐(Queue)에서 쌓이게 되는데, 이 시점에서 클라이언트와의 연결은 아직 완전히 연결되지 않은(not ESTABLISHED state) 대기 상태.
           > 대기 중인 연결 요청을 큐(Queue)로부터 꺼내와서, 연결을 완료하기 위해서는 accept() API를 호출해야 함.
          
        4. accept() : 요청이 수신되면 요청을 받아들이고 데이터 통신을 위한 소켓을 생성
          * 소켓 연결(Connection)을 수립하는 절차
          * 최종적으로 연결 요청을 받아들이는 역할을 수행
          * 주의할 점은 최종적으로 데이터 통신을 위해 연결되는 소켓이, 앞서 bind() 또는 listen() API에서 사용한 서버 소켓(Server Socket)이 아님.
           > 최종적으로 클라이언트 소켓(Client Socket)과 연결이 만들어지는 소켓은 앞서 사용한 서버 소켓(Server Socket)이 아니라, accept API 내부에서 새로 만들어지는 소켓(Socket)
            - 서버 소켓(Server Socket)의 핵심 역할은 클라이언트의 연결 요청을 수신하는 것
            - 이를 위해서 bind() 및 listen()을 통해 소켓에 포트 번호를 바인딩하고 요청 대기 큐를 생성하여 클라이언트의 요청을 대기
            - accept() API에서, 데이터 송수신을 위한 새로운 소켓(Socket)을 만들고 서버 소켓의 대기 큐에 쌓여있는 첫 번째 연결 요청을 매핑. 하나의 연결 요청 처리 종료.            
            - 이후에는 다른 연결 요청을 대기(listen) or 서버 소켓을 닫을(close()) 수 있음
          * 실질적인 데이터 송수신은 accept API에서 생성된, 연결(Connection)이 수립(Established)된 소켓(Socket)을 통해 처리.
          
        5. send()/recv() : [4]에서 생성된 소켓을 통해 연결이 수립되면 데이터를 주고 받음
        
        6. close() : 데이터 송수신이 완료되면 소켓을 닫음


<h3>(2)  Web Socket ? </h3> (ref : https://dev-gorany.tistory.com/212)
  * 목적 : HTTP(Hyper Text Transfer Protocol)를 사용하는 네트워크 데이터 통신의 단점을 보완. <br/>
  > 기존의 단방향 HTTP 프로토콜과 호환되어 양방향 통신을 제공하기 위해 개발된 프로토콜.<br/>
  > 일반 Socket 통신과 달리 HTTP Port를 사용하므로 방화벽에 제약이 없으며 통상 WebSocket으로 불린다.<br/>
  > 접속까지는 HTTP 프로토콜을 이용. 그 이후 통신은 자체적인 Web Socket 프로토콜로 통신.<br/>

![image](https://user-images.githubusercontent.com/108982584/220282016-d7badb17-29d5-43ec-ad53-e5d27c2b141a.png)

      * WebSocket이 기존의 TCP Socket과 다른 점은 최초 접속이 일반 HTTP Request를 통해 HandShaking 과정을 통해 이뤄진다는 점.
      * HTTP Request를 그대로 사용하기 때문에 기존의 80, 443 포트로 접속을 하므로 추가 방화벽을 열지 않고도 양방향 통신이 가능하고, HTTP 규격인 CORS 적용이나 인증 등 과정을 기존과 동일하게 가져갈 수 있는 것이 장점
      * 변경 사항의 빈도가 자주 일어나지 않고, 데이터의 크기가 작은 경우
       > Ajax, Streaming, Long polling 기술이 더 효과적일 수 있음.
      * 실시간성을 보장해야 하고, 변경 사항의 빈도가 잦다면, 또는 짧은 대기 시간, 고주파수, 대용량의 조합인 경우
       > WebSocket이 효과적일 수 있음.

<h4> WebSocket 접속 과정 </h4>

![image](https://user-images.githubusercontent.com/108982584/220282797-6d15d09d-fee1-4ac1-bb0b-11189f2440ab.png)

      * TCP/IP 접속 그리고 웹소켓 열기 HandShake 과정으로 나눌 수 있음.
      * 웹소켓도 TCP/IP위에서 동작하므로, 서버와 클라이언트는 웹소켓을 사용하기 전에 서로 TCP/IP 접속이 되어있어야 함
      * TCP/IP 접속이 완료된 후 서버와 클라이언트는 웹소켓 열기 HandShake 과정을 시작
       > 웹소켓 열기 (HandShake)
        - 클라이언트가 먼저 핸드셰이크 요청을 보내고 이에 대한 응답을 서버가 클라이언트로 보내는 구조
        - 서버와 클라이언트는 HTTP 1.1 프로토콜을 사용하여 요청과 응답을 보냄

<h5> HandShake Request </h5> (ref : https://dev-gorany.tistory.com/212)

![스크린샷(43)](https://user-images.githubusercontent.com/108982584/220284691-425aa56f-8610-4d22-9148-47c861cc3d20.png)


<h5> HandShake Response </h5> (ref : https://dev-gorany.tistory.com/212)

![스크린샷(44)](https://user-images.githubusercontent.com/108982584/220285352-71f1d178-a5fb-4137-bdb0-1664e92f5adb.png)


<h2> 개발노트 </h2>
<h3>1차 : localhost에서 구현 (2023-02-20)</h3> (ref : https://dev-gorany.tistory.com/212) <br/>
 * 문제점 : 모든 클라이언트의 브라우저에서 WebSocket을 지원한다는 보장이 없다. <br/>
           또한, Server/Client 중간에 위치한 Proxy가 Upgrade헤더를 해석하지 못해 서버에 전달하지 못할 수 있다. <br/>
           마지막으로 Server/Client 중간에 위치한 Proxy가 유휴 상태에서 도중에 Connection 종료시킬 수도 있다.<br/>
