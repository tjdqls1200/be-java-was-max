package was.spring.servlet.mvc.view;

import was.request.HttpRequest;
import was.response.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class View {
    private final Path path;

    public View(Path path) {
        this.path = path;
    }

    public void render(Model model, HttpRequest request, HttpResponse response) {
        try {
            response.addResponseBody(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
