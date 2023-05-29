package was.spring.servlet;

import was.spring.servlet.mvc.view.View;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ViewResolver {
    private static final String PREFIX = "/templates/";
    private static final String SUFFIX = ".html";

    public View resolveViewName(String viewName) {
        Path path = getPath(viewName);

        if (path == null || Files.notExists(path)) {
            return null;
        }

        return new View(path);
    }

    private Path getPath(String viewName) {
        try {
            return Path.of(getClass().getResource(format(viewName)).toURI());

        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }

    private String format(String viewName) {
        if (viewName.startsWith(PREFIX) && viewName.endsWith(SUFFIX)) {
            return viewName;
        }
        if (viewName.startsWith("/")) {
            viewName = viewName.substring(1);
        }

        return PREFIX + viewName + SUFFIX;
    }
}
