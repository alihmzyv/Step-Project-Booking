package entities;

import database.Database;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Passenger implements Serializable {
    @Serial
    private static final long serialVersionUID = -3109893404888493359L;

    private final String name;
    private final String surname;



    //constructors
    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static Passenger getRandom() {
        return new Passenger(RandomStringUtils.randomAlphabetic(3, 10),
                RandomStringUtils.randomAlphabetic(3, 10));
    }
    public static List<Passenger> getRandom(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getRandom())
                .collect(Collectors.toCollection(ArrayList::new));
    }


    //getter and setters


    //methods
    //instance methods
    @Override
    public String toString() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name) && Objects.equals(surname, passenger.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
