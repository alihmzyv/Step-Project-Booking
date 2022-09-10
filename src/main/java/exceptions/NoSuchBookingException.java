package exceptions;

public class NoSuchBookingException extends Exception {
    public NoSuchBookingException() {
    }

    public NoSuchBookingException(String message) {
        super(message);
    }
}
