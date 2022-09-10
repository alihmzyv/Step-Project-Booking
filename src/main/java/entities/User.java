package entities;

import database.Database;
import database.dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class User implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -4624705422767438747L;
    private static int idCounter;

    private final int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private List<Booking> bookings;

    static {
        idCounter = Database.getIdCounter("User");
    }

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

    public static int getIdCounter() {
        return idCounter;
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

    public boolean cancelBooking(int bookingIdInput) {
        Optional<Booking> bookingFoundOptional = getBooking(bookingIdInput);
        if (bookingFoundOptional.isEmpty()) {
            return false;
        }
        return bookings.remove(bookingFoundOptional.get());
    }

    public Optional<Booking> getBooking(int bookingId) {
        return bookings.stream()
                .filter(booking -> booking.getId() == bookingId)
                .findFirst();
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
