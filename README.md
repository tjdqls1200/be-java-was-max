# WEB SERVER 구현

<br>

### 1주차

<br>

**구현 목록**

---

- 스레드풀을 생성하고 클라이언트 요청 대기(accept), 요청 오면 RequestHandler 실행 후 소켓 종료 콜백  
- RequestHandler에서 InputStream 파싱, Request 인스턴스 생성  
  - Request  
    - RequestStartLine  
      - HttpMethod method, String url, Map<String, String> requestParams, String version  
      - HttpMethod는 enum 클래스로 생성(GET, POST ..)  
    - HttpHeaders  
      - Map<String, List<String>> headers  
      - HeaderType enum 클래스 생성(ACCEPT, CONTENT_TYPE ..)  
    - String requestBody  
  - 정적 리소스 요청의 경우 FileExtension으로 확장자명(".html"), MIME("text/html") 관리  

<br>


### 2,3주차

<br>

**구현 목록**

---

[x] @RequestMapping 애노테이션과 리플렉션으로 요청에 맞는 컨트롤러 메서드를 찾는 ControllerAdapter 추가  
[x] JDK 동적 프록시로 InvocationHandler 사용 학습 -> 적용은 미정  
[x] ControllerAdapter에서 매핑된 컨트롤러를 찾고 메서드의 파라미터를 만드는 ArgumentResolver 추가  
[ ] GET, POST 메소드에 따라 ArgumentResolver 방식 변경 -> Body 파싱  
[ ] ModelAndView, ViewResolver, ReturnValueHandler  
[ ] Response 생성  
[ ] 쿠키, 세션