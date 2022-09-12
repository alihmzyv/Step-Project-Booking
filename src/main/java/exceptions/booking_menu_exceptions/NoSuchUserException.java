package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class NoSuchUserException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = -8696715282753879186L;

    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
