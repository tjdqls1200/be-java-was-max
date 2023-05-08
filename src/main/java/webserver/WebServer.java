package webserver;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final int port = getPort(args);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Web Application Server started {} port.", port);

            while (isConnected(serverSocket)) {
                executorService.execute(new RequestHandler(serverSocket.accept()));
            }
        }

        executorService.shutdown();
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
