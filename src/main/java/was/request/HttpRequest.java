package was.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpHeaders;
import was.common.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    protected final RequestStartLine startLine;

    protected final HttpHeaders header;

    protected final String requestBody;

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

    public String getVersion() {
        return startLine.getVersion();
    }

    public Map<String, List<String>> getHeaders() {
        return header.getHeaders();
    }

    public List<String> getHeader(String key) {
        return header.getHeader(key);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getParameter(String name) {
        return startLine.getParameter(name);
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        final RequestStartLine startLine = RequestStartLine.parse(br.readLine());
        final HttpHeaders headers = readHeaders(br);
        final String requestBody = readRequestBody(br, headers.getContentLength());

        return new HttpRequest(startLine, headers, requestBody);
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
        char[] body = new char[contentLength];

        br.read(body, 0, contentLength);

        return URLDecoder.decode(String.valueOf(body), StandardCharsets.UTF_8);
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
