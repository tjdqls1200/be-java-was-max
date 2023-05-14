package webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_PRIFIX = "/static";

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (BufferedReader reader = getBufferedReader();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            LOGGER.info("HTTP REQUEST PARSING START");
            final WebRequest request = WebRequest.from(reader);

            LOGGER.info(request.toString());

            LOGGER.info("HTTP REQUEST PARSING COMPLETE");
            FileExtension.from(request.getUrl()).ifPresent(extension -> writeStaticResource(writer, request.getUrl()));
            LOGGER.info("HTTP RESPONSE COMPLETE");

            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void writeStaticResource(BufferedWriter writer, String requestPath) {
        //TODO
        // html, css, ico 등에 따라 처리 필요
        try {
            final String body = Files.readString(toStaticPath(requestPath));
            response200Header(writer, body.length());
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

    private BufferedReader getBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void response200Header(BufferedWriter writer, int contentsLength) {
        try {
            writer.write("HTTP/1.1 200 OK \r\n");
            writer.write("Content-Type: text/html;charset=utf-8\r\n");
            writer.write("Content-Length: " + contentsLength + "\r\n");
            writer.write("\r\n");
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
