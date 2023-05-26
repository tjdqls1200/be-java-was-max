package was.response;

import was.common.HttpHeaders;

public class HttpResponse {
    private int httpStatus = 200;

    private HttpHeaders headers;

    private String responseBody;

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void addStatus(int status) {
        httpStatus = status;
    }

    public void addResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
