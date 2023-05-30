# WEB SERVER 구현

<br>

**동작**

<img width="1025" alt="image" src="https://github.com/tjdqls1200/juniorForum/assets/57752068/1e8defee-4211-439d-91ce-ce0d28e6ff69">

1. HttpRequest 파싱  
   - StartLine  
   - Header  
   - RequestBody  
2. 정적 리소스 요청이 아니면 DispathcerServlet에 요청 전달  
3. ControllerAdapter를 통해 @RequestMapping 정보에 맞는 Controller를 찾음  
4. ArgumentResolver 동작  
   - Controller 메서드의 파라미터에 필요한 값을 변환  
   - RequestBody는 HttpMessageConverter가 변환  
   - 객체는 ObjectBinder가 바인딩  
5. ReturnValueHandler 동작  
   - Controller에서 반환 받은 ViewName, ModelAndView를 변환 (ViewName -> ModelAndView)   
   - 응답에 필요한 처리를 하고 ModelAndView를 반환  
6. ViewResolver  
   - 실제 View 경로를 찾아서 View 인스턴스를 생성, 반환  
7. View  
   - 실제 View 경로에 있는 ~~HTML을 동적으로 렌더링~~  
8 Response 전송  
   
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
[x] GET, POST 메소드에 따라 ArgumentResolver 방식 변경 -> POST는 MeesageConverter가 처리, json은 x  
[x] ~~예외 처리~~, 응답 Response 처리  
[ ] 쿠키, 세션  
[x] 회원 로그인 기능 -> 인증까지만 처리  
[x] ModelAndView, ViewResolver  

<br>

### 4주차

<br>

**구현 목록**

---

[ ] 쿠키, 세션    
[ ] 로그인 유지   
[ ] ~~동적 HTML~~  
[ ] 테스트 코드, 리펙토링 마무리  
<br>
