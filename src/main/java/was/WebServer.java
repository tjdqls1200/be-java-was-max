package was;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final int port = getPort(args);
        final ServerSocket serverSocket = new ServerSocket(port);

        while (isConnected(serverSocket)) {
            Socket connect = serverSocket.accept();
            CompletableFuture.runAsync(new HttpHandler(connect), executorService)
                    .thenRunAsync(closeSocket(connect));
        }

        executorService.shutdown();
    }

    private static boolean isConnected(ServerSocket serverSocket) {
        return !serverSocket.isClosed();
    }

    private static Runnable closeSocket(Socket clientSocket) {
        return () -> {
            try {
                // Closing socket will also close the socket's InputStream and OutputStream.
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static int getPort(String[] args) {
        if (args != null && args.length > 0) {
            return Integer.parseInt(args[0]);
        }

        return DEFAULT_PORT;
    }
}
