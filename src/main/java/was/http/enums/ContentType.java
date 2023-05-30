package was.http.enums;

import java.util.Arrays;

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

    //고민
    FORM_DATA("none", "application/x-www-form-urlencoded"),
    JSON(".json", "application/json");

    private final String extension;

    private final String mimeType;

    ContentType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static ContentType from(String path) {
        return Arrays.stream(values())
                .filter(type -> path.endsWith(type.extension))
                .findFirst()
                .orElse(HTML);
    }

    public static boolean containExtension(String path) {
        return Arrays.stream(values())
                .anyMatch(type -> path.endsWith(type.extension));
    }
}
