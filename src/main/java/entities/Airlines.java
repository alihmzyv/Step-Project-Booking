package entities;

import java.io.Serializable;

public enum Airlines implements Serializable {
    QATAR_AIRWAYS("QTR"),
    TURKISH_AIRLINES("THY");

    private final String ICAOcode;


    Airlines(String ICAOcode) {
        this.ICAOcode = ICAOcode;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",
                Character.toLowerCase(name().charAt(0)) + name().substring(1), ICAOcode);
    }
}
