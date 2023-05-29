package was.response;

import was.common.HttpHeaders;
import was.spring.servlet.http.HttpStatus;

import java.util.Objects;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SP = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private HttpStatus httpStatus;

    private final HttpHeaders headers = new HttpHeaders();

    private String responseBody;

    public void addHeader(String key, String value) {
        headers.addHeader(key, value);
    }

    public void setStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void addResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private String getStartLineFormat() {
        return HTTP_VERSION + SP + httpStatus.getValue() + SP + httpStatus.getPhrase();
    }

    public String getOutputFormat() {
        StringBuilder builder = new StringBuilder();

        builder.append(getStartLineFormat()).append(LINE_SEPARATOR);
        builder.append(headers.toHeadersOutputFormat()).append(LINE_SEPARATOR);

        if (Objects.isNull(responseBody)) {
            return builder.toString();
        }

        builder.append(LINE_SEPARATOR).append(responseBody).append(LINE_SEPARATOR);

        return builder.toString();
    }
}
