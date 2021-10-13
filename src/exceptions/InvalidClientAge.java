package exceptions;

public class InvalidClientAge extends RuntimeException {

    public InvalidClientAge(String message) {
        super(message);
    }

    public InvalidClientAge(String message, Throwable cause) {
        super(message, cause);
    }

}
