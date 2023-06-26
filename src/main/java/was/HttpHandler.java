package was;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.enums.ContentType;
import was.http.enums.HeaderType;
import was.spring.servlet.DispatcherServlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);
    private static final String STATIC_PRIFIX = "/static";
    private static final byte[] LINE_SEPARATOR = System.lineSeparator().getBytes();

    private final BufferedReader in;
    private final DataOutputStream out;

    public HttpHandler(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        out = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try (in; out) {
            final HttpRequest request = HttpRequest.parse(in);
            final HttpResponse response = new HttpResponse();
            LOGGER.info(request.toString());
            handle(request, response);
            sendResponse(request, response);

        } catch (IOException | IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.info("HTTP RESPONSE COMPLETE");
    }

    private void handle(final HttpRequest request, final HttpResponse response) {
        if (isStaticResourceRequest(request)) {
            readStaticResource(request, response);
            return;
        }

        DispatcherServlet dispatcherServlet = DispatcherServlet.getInstance();
        dispatcherServlet.doDispatch(request, response);
    }

    private void readStaticResource(HttpRequest request, HttpResponse response) {
        try {
            final byte[] staticResourceBytes = Files.readAllBytes(formatStaticPath(request.getUrl()));
            response.setResponseBody(staticResourceBytes);
        } catch (Exception ex) {
            throw new IllegalArgumentException("해당 리소스가 없습니다.");
        }
    }

    private boolean isStaticResourceRequest(HttpRequest request) {
        return ContentType.containExtension(request.getUrl());
    }

    private void sendResponse(final HttpRequest request, final HttpResponse response) {
        if (response.hasResponseBody()) {
            addResponseBodyHeader(request, response);
        }

        try {
            out.write(response.getStartLineFormat().getBytes());
            out.write(LINE_SEPARATOR);

            out.write(response.getHeadersFormat().getBytes());
            out.write(LINE_SEPARATOR);

            out.write(LINE_SEPARATOR);
            out.write(response.getResponseBody());
            out.write(LINE_SEPARATOR);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addResponseBodyHeader(HttpRequest request, HttpResponse response) {
        final ContentType contentType = ContentType.from(request.getUrl());
        final byte[] responseBody = response.getResponseBody();

        response.addHeader(HeaderType.CONTENT_TYPE, contentType.getMimeType());
        response.addHeader(HeaderType.CONTENT_LENGTH, String.valueOf(responseBody.length));
    }

    private Path formatStaticPath(String requestPath) throws URISyntaxException {
        return Path.of(getClass().getResource(STATIC_PRIFIX + requestPath).toURI());
    }
}
