package entities;

import java.io.Serializable;

public enum Airport implements Serializable {
    DUBAI("AE", "DXB"),
    ISTANBUL("TR", "IST");


    private final String countryCode;
    private final String code;

    Airport(String countryCode, String code) {
        this.countryCode = countryCode;
        this.code = code;
    }
}
