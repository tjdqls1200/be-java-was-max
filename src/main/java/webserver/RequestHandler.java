package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (BufferedReader reader = getBufferedReader(); OutputStream out = clientSocket.getOutputStream()) {

            final WebRequest request = WebRequest.from(reader);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);

            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("------------- run exit ------------------");
    }

    private BufferedReader getBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
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
