package exceptions.menu_exceptions;

import java.io.Serial;

public class NoSuchMenuItemException extends MenuException {
    @Serial
    private static final long serialVersionUID = -6351186997406595571L;


    //constructors
    public NoSuchMenuItemException() {
        super();
    }

    public NoSuchMenuItemException(String message) {
        super(message);
    }
}
