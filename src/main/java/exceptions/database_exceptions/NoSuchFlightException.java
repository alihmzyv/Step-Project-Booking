package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class NoSuchFlightException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = -7417111988485716961L;


    //constructors
    public NoSuchFlightException() {
    }

    public NoSuchFlightException(String message) {
        super(message);
    }
}
