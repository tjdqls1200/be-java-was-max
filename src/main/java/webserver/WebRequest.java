package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRequest.class);

    private final RequestStartLine startLine;

    private final HttpHeaders header;

    private final String requestBody;

    protected WebRequest(RequestStartLine startLine, HttpHeaders header, String requestBody) {
        this.startLine = startLine;
        this.header = header;
        this.requestBody = requestBody;
    }

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public String getUrl() {
        return startLine.getUrl();
    }

    public Map<String, String> getRequestParams() {
        return startLine.getRequestParams();
    }

    public String getVersion() {
        return startLine.getVersion();
    }

    public Map<String, List<String>> getHeaders() {
        return header.getHeaders();
    }

    public String getRequestBody() {
        return requestBody;
    }

    public static WebRequest from(BufferedReader br) throws IOException {
        return new WebRequest(
                readStartLine(br.readLine()),
                readHeaders(br),
                readRequestBody(br)
        );
    }

    private static RequestStartLine readStartLine(String startLine) {
        LOGGER.info("PARSE START LINE");
        if (startLine == null || startLine.isBlank()) {
            throw new IllegalArgumentException("Invalid Http Requst");
        }

        return RequestStartLine.parseStartLine(startLine);
    }

    private static HttpHeaders readHeaders(BufferedReader br) throws IOException {
        LOGGER.info("PARSE HEADERS");
        final List<String> headerLines = new ArrayList<>();
        String headerLine;

        while ((headerLine = br.readLine()) != null && !headerLine.isBlank()) {
            headerLines.add(headerLine);
        }

        return HttpHeaders.parse(headerLines);
    }

    private static String readRequestBody(BufferedReader br) throws IOException {
        LOGGER.info("PARSE REQUEST BODY");
        StringBuilder bodyBuilder = new StringBuilder();
        String readLine;

        if (br.ready()) {
            while ((readLine = br.readLine()) != null && !readLine.isBlank()) {
                bodyBuilder.append(readLine);
            }
        }

        return bodyBuilder.toString();
    }

    @Override
    public String toString() {
        return "WebRequest{" + '\n' +
                startLine  + '\n' +
                header + '\n' +
                requestBody + '\n' +
                '}';
    }
}
