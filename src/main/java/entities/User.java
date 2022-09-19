package entities;

import database.Database;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.*;

public class User implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -4624705422767438747L;
    private static int idCounter;

    private final int id;
    private final String name;
    private final String surname;
    private final String username;
    private final String password;
    private final List<Booking> bookings;

    static {
        idCounter = Database.getIdCounter("User");
    }


    //constructors
    public User(String name, String surname, String username, String password) {
        this.id = idCounter++;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        bookings = new ArrayList<>();
    }

    //getter and setters
    @Override
    public int getId() {
        return id;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //methods
    //instance methods
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public boolean removeBooking(Booking booking) {
        return bookings.remove(booking);
    }

    public boolean hasBooking(Booking booking) {
        return bookings.contains(booking);
    }

    public static User getRandom() {
        return new User(RandomStringUtils.randomAlphabetic(3, 10),
                RandomStringUtils.randomAlphabetic(3, 10),
                RandomStringUtils.randomAlphanumeric(3, 10),
                RandomStringUtils.randomGraph(6, 10));
    }

    public static List<User> getRandom(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getRandom())
                .collect(Collectors.toCollection(ArrayList::new));
    }


    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
