package entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public enum Airline implements Serializable {
    QATAR_AIRWAYS("QTR", "QR"),
    TURKISH_AIRLINES("THY", "TK"),
    SINGAPORE_AIRLINES("SIA", "SQ"),
    ANA_ALL_NIPPON_AIRWAYS("ANA", "NH"),
    EMIRATES("UAE", "EK"),
    JAPAN_AIRLINES("JAL", "JL"),
    CATHAY_PACIFIC_AIRWAYS("CPA", "CX"),
    EVA_AIR("EVA", "BR"),
    QANTAS_AIRWAYS("QFA", "QF"),
    AZERBAIJAN_AIRLINES("AHY", "J2");


    private final String ICAOcode;
    private final String IATADesignator;


    //constructors
    Airline(String ICAOcode, String IATADesignator) {
        this.ICAOcode = ICAOcode;
        this.IATADesignator = IATADesignator;
    }


    //getter and setters
    public String getIATADesignator() {
        return IATADesignator;
    }


    //methods
    //static methods
    public static Airline getRandom() {
        return Airline.values()[new Random().nextInt(Airline.values().length)];
    }

    //instance methods
    /*
     * returns the name of the Airline enum constant in the format
     * in which each word starts with capital letter, e.g. TURKISH_AIRLINES --> Turkish Airlines
     */
    private String getName() {
        return String.join(" ",
                Arrays.stream(name().split("_"))
                        .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                        .toList());
    }

    /*
     * returns the String representation of the Airline enum in the following format,
     * e.g. TURKISH_AIRLINES --> Turkish Airlines (THY)
     */
    @Override
    public String toString() {
        return String.format("%s (%s)",
                getName(), ICAOcode);
    }
}
