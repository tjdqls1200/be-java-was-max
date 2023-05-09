package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRequest.class);

    private String method;

    private String url;

    private String version;

    private final Map<String, String> headers = new HashMap<>();

    private final Map<String, String> requestParams = new HashMap<>();

    protected WebRequest() {
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    protected void setMethod(String method) {
        this.method = method;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected void setRequestParam(String key, String value) {
        requestParams.put(key, value);
    }

    protected void setVersion(String version) {
        this.version = version;
    }

    protected void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public static WebRequest from(BufferedReader br) throws IOException {
        final WebRequest request = new WebRequest();
        int lineNumber = 0;
        String line;

        while ((line = br.readLine()) != null && !line.isBlank()) {
            LOGGER.info(line);
            HttpParser.parse(request, line, lineNumber++);
        }

        return request;
    }
}
