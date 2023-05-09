package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum FileExtension {
    HTML(".html"),
    CSS(".css"),
    JS(".js"),
    PNG(".png"),
    JPG(".jpg"),
    ICON(".ico");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public static Optional<FileExtension> from(String path) {
        return Arrays.stream(values())
                .filter(fileExtension -> path.endsWith(fileExtension.extension))
                .findFirst();
    }
}
