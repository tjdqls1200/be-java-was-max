package was.spring.servlet.mvc.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class View {
    private static final Logger LOGGER = LoggerFactory.getLogger(View.class);
    private final Path path;

    public View(Path path) {
        this.path = path;
    }

    public void render(Model model, HttpRequest request, HttpResponse response) {
        try {
            response.addResponseBody(Files.readString(path));
        } catch (IOException e) {
            LOGGER.info(e.getClass().getName());
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
