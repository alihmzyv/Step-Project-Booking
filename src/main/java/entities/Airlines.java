package entities;

import java.io.Serializable;

public enum Airlines implements Serializable {
    DELTA_AIR_LINES("DAL"),
    AMERICAN_AIRLINES_GROUP("AAL");

    private final String code;


    Airlines(String code) {
        this.code = code;
    }
}
