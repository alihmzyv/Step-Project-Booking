package entities;

import database.dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean cancelBooking(int bookingIdInput) {
        Optional<Booking> bookingFoundOptional = getBooking(id);
        if (bookingFoundOptional.isEmpty()) {
            return false;
        }
        bookings.remove(bookingFoundOptional.get());
        return true;
    }

    public Optional<Booking> getBooking(int id) {
        return bookings.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
