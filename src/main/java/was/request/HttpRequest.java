package was.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpHeaders;
import was.common.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestStartLine startLine;

    private final HttpHeaders header;

    private final String requestBody;

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

    public static HttpRequest from(BufferedReader br) throws IOException {
        return new HttpRequest(
                RequestStartLine.parse(br.readLine()),
                readHeaders(br),
                readRequestBody(br)
        );
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

    private static String readRequestBody(BufferedReader br) throws IOException {
        if (isEmptyBuffer(br)) {
            return "";
        }

        final StringBuilder bodyBuilder = new StringBuilder();
        String line = br.readLine();

        while (hasReadLine(line)) {
            bodyBuilder.append(line);
            line = br.readLine();
        }

        return bodyBuilder.toString();
    }

    private static boolean isEmptyBuffer(BufferedReader br) throws IOException {
        return !br.ready();
    }

    private static boolean hasReadLine(String line) {
        return line != null && !line.isBlank();
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
