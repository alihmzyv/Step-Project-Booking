package entities;

import java.util.Arrays;

public enum City {
    BAKU,
    ISTANBUL,
    ATLANTA,
    GUANGZHOU,
    DELHI,
    MEXICO_CITY,
    KHIMKI,
    DUBAI,
    PARIS;


    //methods
    /*
     * returns the String representation of the City enum constant in the following format,
     * e.g., MEXICO_CITY --> Mexico City
     */
    @Override
    public String toString() {
        return String.join(" ",
                Arrays.stream(name().split("_"))
                        .map(word -> word.charAt(0) + word.substring(1))
                        .toList());
    }
}
