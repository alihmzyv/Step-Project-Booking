package entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import static entities.City.*;
import static entities.Country.*;

public enum Airport implements Serializable {
    HEYDAR_ALIYEV_INTERNATIONAL_AIRPORT("GYD", BAKU, AZERBAIJAN),
    ISTANBUL_AIRPORT("IST",  ISTANBUL, TURKEY),
    HARTSFIELD_JACKSON_ATLANTA_INTERNATIONAL_AIRPORT("ATL", ATLANTA, USD),
    GUANGZHOU_BAIYUN_INTERNATIONAL_AIRPORT("CAN", GUANGZHOU, CHINA),
    INDIRA_GANDHI_INTERNATIONAL_AIRPORT("DEL", DELHI, INDIA),
    MEXICO_CITY_INTERNATIONAL_AIRPORT("MEX", MEXICO_CITY, MEXICO),
    SHEREMETYEVO_INTERNATIONAL_AIRPORT("SVO", KHIMKI, RUSSIA),
    DUBAI_INTERNATIONAL_AIRPORT("DXB", DUBAI, UAE),
    CHARLES_DE_GAULLE_AIRPORT("CDG", PARIS, FRANCE),
    ISTANBUL_SABIHA_GOKCEN_INTERNATIONAL_AIRPORT("SAW", ISTANBUL, TURKEY);


    private final String IATAcode;
    private final City city;
    private final Country country;


    //constructors
    Airport(String IATAcode, City city, Country country) {
        this.IATAcode = IATAcode;
        this.city = city;
        this.country = country;
    }


    //methods
    //static methods
    /*
     * returns a random enum constant of Airport
     */
    public static Airport getRandom() {
        return Airport.values()[new Random().nextInt(Airport.values().length)];
    }

    /*
     * returns a random enum constant of Airport, different from the specified Airport enum constant
     * that - the enum constant which should not be returned
     */
    public static Airport getRandomExcept(Airport that) {
        return Arrays.stream(Airport.values())
                .filter(airport -> airport != that)
                .toArray(Airport[]::new)[new Random().nextInt(Airport.values().length - 1)];
    }

    //instance methods
    /*
     * returns the String returned by calling toString() on the field "city"
     */
    public String getCity() {
        return city.toString();
    }

    /*
     * returns the String representation of the Airport enum in the following format,
     * e.g., ISTANBUL_AIRPORT --> Istanbul (IST)
     */
    @Override
    public String toString() {
        return String.format("%s (%s)",
                getCity(), IATAcode);
    }
}
