package webserver;

import java.util.Arrays;

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

    public static boolean hasExtension(String path) {
        return Arrays.stream(values())
                .anyMatch(fileExtension -> path.endsWith(fileExtension.extension));
    }
}
