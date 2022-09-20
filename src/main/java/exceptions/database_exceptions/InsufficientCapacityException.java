package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class InsufficientCapacityException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = -7195584141051756343L;


    //constructors
    public InsufficientCapacityException(String message) {
        super(message);
    }
}
