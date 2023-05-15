package was;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.request.RequestHandler;

public class WebServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        final int port = getPort(args);
        final ServerSocket serverSocket = new ServerSocket(port);

        while (isConnected(serverSocket)) {
            LOGGER.info("Active Thread: " + executorService.getActiveCount());

            Socket clientSocket = serverSocket.accept();

            CompletableFuture.runAsync(toRequestHandler(clientSocket), executorService)
                    .thenRunAsync(closeSocket(clientSocket));
        }

        executorService.shutdown();
        serverSocket.close();
    }


    private static RequestHandler toRequestHandler(Socket clientSocket) throws IOException {
        return new RequestHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
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

    private static boolean isConnected(ServerSocket serverSocket) {
        return !serverSocket.isClosed();
    }
}
