package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class NoSuchFlightException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = -7417111988485716961L;

    public NoSuchFlightException() {
    }

    public NoSuchFlightException(String message) {
        super(message);
    }
}
