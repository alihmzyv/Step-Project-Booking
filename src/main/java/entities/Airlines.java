package entities;

import java.io.Serializable;

public enum Airlines implements Serializable {
    QATAR_AIRWAYS("QTR", "QR"),
    TURKISH_AIRLINES("THY", "TK");

    private final String ICAOcode;
    private final String IATADesignator;

    Airlines(String ICAOcode, String IATADesignator) {
        this.ICAOcode = ICAOcode;
        this.IATADesignator = IATADesignator;
    }

    public String getIATADesignator() {
        return IATADesignator;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",
                Character.toLowerCase(name().charAt(0)) + name().substring(1), ICAOcode);
    }
}
