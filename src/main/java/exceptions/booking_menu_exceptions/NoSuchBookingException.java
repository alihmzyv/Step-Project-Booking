package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class NoSuchBookingException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = 4399762760860963948L;

    public NoSuchBookingException() {
    }

    public NoSuchBookingException(String message) {
        super(message);
    }
}
