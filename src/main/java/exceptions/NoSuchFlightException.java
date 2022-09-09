package exceptions;

public class NoSuchFlightException extends Exception {
    public NoSuchFlightException() {
    }

    public NoSuchFlightException(String message) {
        super(message);
    }
}
