package entities;

import java.io.Serializable;

import static entities.City.*;
import static entities.Country.*;

public enum Airport implements Serializable {
    HEYDAR_ALIYEV_INTERNATIONAL_AIRPORT("GYD", BAKU, AZERBAIJAN),
    ISTANBUL_AIRPORT("IST",  ISTANBUL, TURKEY);


    private final String IATAcode;
    private final City city;
    private Country country;

    Airport(String IATAcode, City city, Country country) {
        this.IATAcode = IATAcode;
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city.name().toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s)\n",
                city, Character.toLowerCase(name().charAt(0)) + name().substring(1), IATAcode);
    }
}
