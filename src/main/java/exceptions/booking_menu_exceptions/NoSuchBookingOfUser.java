package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class NoSuchBookingOfUser extends NoSuchBookingException {
    @Serial
    private static final long serialVersionUID = -3648091254317185786L;

    public NoSuchBookingOfUser() {
    }

    public NoSuchBookingOfUser(String message) {
        super(message);
    }
}
