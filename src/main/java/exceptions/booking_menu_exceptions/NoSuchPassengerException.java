package exceptions.booking_menu_exceptions;

import entities.Booking;

import java.io.Serial;

public class NoSuchPassengerException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = -3791906420126594179L;

    public NoSuchPassengerException() {
    }

    public NoSuchPassengerException(String message) {
        super(message);
    }

    public NoSuchPassengerException(Throwable cause) {
        super(cause);
    }
}
