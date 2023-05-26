package was.spring.servlet.http;

public enum HttpStatus {
    OK(200),
    CREATED(201),
    REDIRECT(302),
    FORBIDDEN(403),
    BAD_REQUEST(404);

    private final int value;

    HttpStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
