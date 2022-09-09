package entities;

import database.dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -4624705422767438747L;
    private static int idCounter = 1;

    private final int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private List<Booking> bookings;

    public User(String name, String surname, String username, String password, List<Booking> bookings) {
        this.id = idCounter++;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.bookings = bookings;
    }

    public User(String name, String surname, String username, String password) {
        this.id = idCounter++;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        bookings = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
