package exceptions;

import java.io.Serial;

public class MenuException extends Exception {
    @Serial
    private static final long serialVersionUID = 1631981010431577475L;

    public MenuException() {
    }

    public MenuException(String message) {
        super(message);
    }
}
