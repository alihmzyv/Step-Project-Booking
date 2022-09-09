package entities;

import database.dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;

public class Passenger implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -3109893404888493359L;
    private static int idCounter = 1;

    private final int id;
    private String name;
    private String surname;

    //constructors
    public Passenger(String name, String surname) {
        this.id = idCounter++;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public int getId() {
        return id;
    }
}
