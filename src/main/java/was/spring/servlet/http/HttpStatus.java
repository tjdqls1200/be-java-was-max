package was.spring.servlet.http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    REDIRECT(302, "Found"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found");

    private final int value;
    private final String phrase;

    HttpStatus(int value, String phrase) {
        this.value = value;
        this.phrase = phrase;
    }

    public int getValue() {
        return value;
    }

    public String getPhrase() {
        return phrase;
    }
}
