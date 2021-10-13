package exceptions;

public class InvalidCurrencyType extends RuntimeException {

    public InvalidCurrencyType(String message) {
        super(message);
    }

    public InvalidCurrencyType(String message, Throwable cause) {
        super(message, cause);
    }

}
