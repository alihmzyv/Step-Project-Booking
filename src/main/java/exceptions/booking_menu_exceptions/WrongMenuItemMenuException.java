package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class WrongMenuItemMenuException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = -6351186997406595571L;

    public WrongMenuItemMenuException() {
        super();
    }

    public WrongMenuItemMenuException(String message) {
        super(message);
    }
}
