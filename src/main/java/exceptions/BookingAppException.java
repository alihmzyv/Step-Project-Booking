package exceptions;

import java.io.Serial;

public class BookingAppException extends Exception {
    @Serial
    private static final long serialVersionUID = 1066661280589889545L;

    public BookingAppException() {
        super();
    }

    public BookingAppException(String message) {
        super(message);
    }
}
