package exceptions;

public class InvalidHumanData extends Exception {

    public InvalidHumanData(String message) {
        super(message);
    }

    public InvalidHumanData(String message, Throwable cause) {
        super(message, cause);
    }

}
