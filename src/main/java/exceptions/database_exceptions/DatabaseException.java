package exceptions.database_exceptions;

import exceptions.BookingAppException;

import java.io.Serial;

public class DatabaseException extends BookingAppException {
    @Serial
    private static final long serialVersionUID = 2904944401184423944L;


    //constructors
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
