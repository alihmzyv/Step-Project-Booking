package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class NoSuchUserException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = -8696715282753879186L;


    //constructors
    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
