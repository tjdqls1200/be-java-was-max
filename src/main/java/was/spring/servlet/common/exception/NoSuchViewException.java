package was.spring.servlet.common.exception;

import was.spring.servlet.common.HttpStatus;

public class NoSuchViewException extends RuntimeException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public NoSuchViewException() {
        super();
    }

    public NoSuchViewException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
