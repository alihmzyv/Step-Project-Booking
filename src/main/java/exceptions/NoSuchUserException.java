package exceptions;

public class NoSuchUserException extends BookingAppException {
    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
