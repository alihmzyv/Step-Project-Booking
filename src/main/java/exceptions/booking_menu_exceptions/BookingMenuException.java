package exceptions.booking_menu_exceptions;

import exceptions.MenuException;

import java.io.Serial;

public class BookingMenuException extends MenuException {
    @Serial
    private static final long serialVersionUID = 1066661280589889545L;

    public BookingMenuException() {
        super();
    }

    public BookingMenuException(String message) {
        super(message);
    }

    public BookingMenuException(Throwable cause) {
        super(cause);
    }
}
