package exceptions;

public class WrongMenuItemAppException extends BookingAppException {
    public WrongMenuItemAppException() {
        super();
    }

    public WrongMenuItemAppException(String message) {
        super(message);
    }
}
