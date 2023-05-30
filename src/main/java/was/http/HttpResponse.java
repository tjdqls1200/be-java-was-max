package was.http;

import was.http.enums.HeaderType;
import was.spring.servlet.common.HttpStatus;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SP = " ";

    private HttpStatus httpStatus = HttpStatus.OK;

    private final HttpHeaders headers = new HttpHeaders();

    private byte[] responseBody = new byte[0];

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void addHeader(HeaderType headerType, String value) {
        headers.addHeader(headerType.getKey(), value);
    }

    public void setStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody.getBytes(StandardCharsets.UTF_8);
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public boolean hasResponseBody() {
        return responseBody.length > 0;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public String getStartLineFormat() {
        return HTTP_VERSION + SP + httpStatus.getValue() + SP + httpStatus.getPhrase();
    }

    public String getHeadersFormat() {
        return headers.toHeadersOutputFormat();
    }
}