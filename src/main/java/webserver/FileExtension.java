package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum FileExtension {
    HTML(".html", "text/html;charset=utf-8"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpg"),
    ICON(".ico", "image/x-icon"),
    TRUE_TYPE_FONT(".ttf", "application/x-font-ttf"),
    WEB_OPEN_FONT(".woff", "application/x-font-woff"),
    SCALABLE_VECTOR_GRAPHICS(".svg", "image/svg+xml");

    private final String extension;

    private final String mimeType;

    FileExtension(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static Optional<FileExtension> from(String path) {
        return Arrays.stream(values())
                .filter(fileExtension -> path.endsWith(fileExtension.extension))
                .findFirst();
    }
}
