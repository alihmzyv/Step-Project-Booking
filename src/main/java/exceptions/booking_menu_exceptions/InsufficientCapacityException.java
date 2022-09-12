package exceptions.booking_menu_exceptions;

import exceptions.booking_menu_exceptions.BookingMenuException;

import java.io.Serial;

public class InsufficientCapacityException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = -7195584141051756343L;

    public InsufficientCapacityException(String message) {
        super(message);
    }
}
