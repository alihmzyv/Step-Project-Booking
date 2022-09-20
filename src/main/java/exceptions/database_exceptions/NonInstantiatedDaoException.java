package exceptions.database_exceptions;

import exceptions.menu_exceptions.MenuException;

import java.io.Serial;

public class NonInstantiatedDaoException extends DatabaseException {
    @Serial
    private static final long serialVersionUID = 6088087568183212942L;


    //constructors
    public NonInstantiatedDaoException() {
    }

    public NonInstantiatedDaoException(String message) {
        super(message);
    }

    public NonInstantiatedDaoException(Throwable cause) {
        super(cause);
    }
}
