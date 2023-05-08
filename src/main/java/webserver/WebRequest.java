package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class WebRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRequest.class);

    private String method;

    private String url;

    private String version;

    private final Map<String, String> headers = new HashMap<>();

    /*
    * GET /index.html HTTP/1.1
    * Host: localhost:8080
    * Connection: keep-alive
    * Accept:
    * */

    protected WebRequest() {
    }

    protected void setMethod(String method) {
        this.method = method;
    }

    protected void setUrl(String url) {
        this.url = url;
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

        while ((line = br.readLine()) != null) {
            LOGGER.info(line);
            HttpParser.parse(request, line, lineNumber++);
        }

        return request;
    }
}
