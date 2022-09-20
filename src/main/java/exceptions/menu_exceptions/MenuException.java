package exceptions.menu_exceptions;

import exceptions.BookingAppException;

import java.io.Serial;

public class MenuException extends BookingAppException {
    @Serial
    private static final long serialVersionUID = 1066661280589889545L;


    //constructors
    public MenuException() {
        super();
    }

    public MenuException(String message) {
        super(message);
    }

    public MenuException(Throwable cause) {
        super(cause);
    }
}
