package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class LocalDatabaseException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = 9179985488303832086L;


    //constructors
    public LocalDatabaseException() {
    }

    public LocalDatabaseException(String message) {
        super(message);
    }

    public LocalDatabaseException(Throwable cause) {
        super(cause);
    }
}
