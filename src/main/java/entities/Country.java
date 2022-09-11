package entities;

import java.util.Arrays;

public enum Country {
    AZERBAIJAN,
    TURKEY,
    USD,
    CHINA,
    INDIA,
    MEXICO,
    RUSSIA,
    UAE,
    FRANCE;


    //methods
    /*
     * returns the String representation of the Country enum constant in the following format,
     * e.g., AZERBAIJAN --> Azerbaijan
     */
    @Override
    public String toString() {
        return String.join(" ",
                Arrays.stream(name().split("_"))
                        .map(word -> word.charAt(0) + word.substring(1))
                        .toList());
    }
}
