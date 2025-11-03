package exceptions;

public class MobileNumberMismatchException extends Exception {

    public MobileNumberMismatchException() {
    }

    public MobileNumberMismatchException(String message) {
        super(message);
    }

    public MobileNumberMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
