package exceptions;

public class isValidMobileNoException extends RuntimeException {
    public isValidMobileNoException() {
    }

    public isValidMobileNoException(String message) {
        super(message);
    }

    public isValidMobileNoException(String message, Throwable cause) {
        super(message, cause);
    }
}
