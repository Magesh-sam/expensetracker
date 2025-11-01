package exceptions;

public class InvalidMobileNumberException extends RuntimeException {

    public InvalidMobileNumberException() {
    }

    public InvalidMobileNumberException(String message) {
        super(message);
    }

    public InvalidMobileNumberException(String message, Throwable cause) {
        super(message, cause);
    }

}
