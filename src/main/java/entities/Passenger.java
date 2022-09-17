package entities;

import database.Database;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Passenger implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -3109893404888493359L;
    private static int idCounter;

    private final int id;
    private final String name;
    private final String surname;


    static {
        idCounter = Database.getIdCounter("Passenger");
    }


    //constructors
    public Passenger(String name, String surname) {
        this.id = idCounter++;
        this.name = name;
        this.surname = surname;
    }


    //getter and setters
    @Override
    public int getId() {
        return id;
    }


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
        return id == passenger.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
