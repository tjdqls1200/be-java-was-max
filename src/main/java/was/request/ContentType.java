package was.request;

import java.util.Arrays;
import java.util.Optional;

public enum ContentType {
    HTML(".html", "text/html;charset=utf-8"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpg"),
    ICON(".ico", "image/x-icon"),
    TRUE_TYPE_FONT(".ttf", "application/x-font-ttf"),
    WEB_OPEN_FONT(".woff", "application/x-font-woff"),
    SCALABLE_VECTOR_GRAPHICS(".svg", "image/svg+xml"),

    //TODO json, form 데이터는 extension이 없는데 ContentType 구조 고민
    FORM_DATA("none", "application/x-www-form-urlencoded");

    private final String extension;

    private final String mimeType;

    ContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static Optional<ContentType> from(String path) {
        return Arrays.stream(values())
                .filter(fileExtension -> path.endsWith(fileExtension.extension))
                .findFirst();
    }
}
