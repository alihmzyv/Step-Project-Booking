package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class NoSuchBookingException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = 4399762760860963948L;


    //constructors
    public NoSuchBookingException() {
    }

    public NoSuchBookingException(String message) {
        super(message);
    }
}
