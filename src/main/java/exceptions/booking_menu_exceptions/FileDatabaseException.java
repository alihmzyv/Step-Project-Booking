package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class FileDatabaseException extends BookingMenuException {
    public FileDatabaseException() {
    }

    public FileDatabaseException(String message) {
        super(message);
    }

    public FileDatabaseException(Throwable cause) {
        super(cause);
    }

    @Serial
    private static final long serialVersionUID = 9179985488303832086L;
}
