package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_PRIFIX = "/static";

    private final InputStream in;
    private final OutputStream out;

    public RequestHandler(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             DataOutputStream writer = new DataOutputStream(out)) {

            LOGGER.info("HTTP REQUEST PARSING START");
            final WebRequest request = WebRequest.from(reader);

            LOGGER.info(request.toString());
            LOGGER.info("HTTP REQUEST PARSING COMPLETE");

            final FileExtension extension = FileExtension.from(request.getUrl())
                    .orElseThrow(() -> new IllegalArgumentException("UNSUPPORTED EXTENSION"));

            writeStaticResource(writer, request.getUrl(), extension);

            LOGGER.info("HTTP RESPONSE COMPLETE");
        } catch (IOException | IllegalArgumentException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    private void writeStaticResource(DataOutputStream writer, String requestPath, FileExtension extension) {
        //TODO
        // html, css, ico 등에 따라 처리 필요
        try {
            final byte[] body = Files.readAllBytes(toStaticPath(requestPath));
            response200Header(writer, extension.getMimeType(), body.length);
            writer.write(body);
            writer.flush();
        } catch (IOException | URISyntaxException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private Path toStaticPath(String requestPath) throws URISyntaxException {
        LOGGER.info("REQUEST PATH = " + requestPath);
        if ("/".equals(requestPath)) {
            requestPath = "/index.html";
        }
        return Path.of(Objects.requireNonNull(getClass().getResource(STATIC_PRIFIX + requestPath)).toURI());
    }

    private void response200Header(DataOutputStream writer, String mimeType, int contentsLength) {
        try {
            writer.write("HTTP/1.1 200 OK \r\n".getBytes());
            writer.write(("Content-Type: " + mimeType + "\r\n").getBytes());
            writer.write(("Content-Length: " + contentsLength + "\r\n").getBytes());
            writer.write("\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
