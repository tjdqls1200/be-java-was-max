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

**구현 계획**

---

- Response 클래스 생성  
- 동적 Request를 처리하는 클래스 생성  