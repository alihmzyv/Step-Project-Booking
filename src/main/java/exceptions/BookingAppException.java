package exceptions;

import java.io.Serial;

public class BookingAppException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1631981010431577475L;


    //constructors
    public BookingAppException() {
    }

    public BookingAppException(String message) {
        super(message);
    }

    public BookingAppException(Throwable cause) {
        super(cause);
    }
}
