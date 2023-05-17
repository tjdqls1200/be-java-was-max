package was.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HeaderType;
import was.common.HttpHeaders;
import was.common.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestStartLine startLine;

    private final HttpHeaders header;

    private final String requestBody;

    private final Map<String, String> attributes = new HashMap<>();

    protected HttpRequest(RequestStartLine startLine, HttpHeaders header, String requestBody) {
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

    public Map<String, String> getAttributes() {
        return new HashMap<>(attributes);
    }

    public Optional<String> getAttribute(String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName));
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        final RequestStartLine startLine = RequestStartLine.parse(br.readLine());
        final HttpHeaders headers = readHeaders(br);
        final String requestBody = readRequestBody(br, headers.getContentLength());

        final HttpRequest request = new HttpRequest(startLine, headers, requestBody);

        //TODO 리펙토링
        request.parseAttributes(headers, requestBody);

        return request;
    }

    private void parseAttributes(HttpHeaders headers, String requestBody) {
        final String contentType = headers.getHeader(HeaderType.CONTENT_TYPE.getKey()).get(0);

        if ("application/x-www-form-urlencoded".equals(contentType)) {
            for (String formDataEntry : requestBody.split("&")) {
                String[] formData = formDataEntry.split("=");
                attributes.put(formData[0], formData[1]);
            }
        }
    }

    private static HttpHeaders readHeaders(BufferedReader br) throws IOException {
        final List<String> headerLines = new ArrayList<>();
        String line = br.readLine();

        while (hasReadLine(line)) {
            headerLines.add(line);
            line = br.readLine();
        }

        return HttpHeaders.parse(headerLines);
    }

    private static String readRequestBody(BufferedReader br, final int contentLength) throws IOException {
        final char[] body = new char[contentLength];

        br.read(body);

        return new String(body);
    }

    private static boolean hasReadLine(String line) {
        return line != null && !line.isBlank();
    }

    @Override
    public String toString() {
        return "WebRequest{" + '\n' +
                startLine  + '\n' +
                header + '\n' +
                "requestBody {\n\t" + requestBody + '\n' +
                '}';
    }
}
