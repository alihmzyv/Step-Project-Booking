package exceptions.booking_menu_exceptions;

import java.io.Serial;

public class NonInitializedDatabaseException extends BookingMenuException {
    @Serial
    private static final long serialVersionUID = 6088087568183212942L;

    public NonInitializedDatabaseException() {
    }

    public NonInitializedDatabaseException(String message) {
        super(message);
    }

    public NonInitializedDatabaseException(Throwable cause) {
        super(cause);
    }
}
