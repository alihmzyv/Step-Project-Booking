package entities;

import database.Database;
import database.dao.Identifiable;

import java.awt.print.Book;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class Booking implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -2078227854687969036L;
    private static int idCounter;


    private final int id;
    private Flight flight;
    private User user;
    private Passenger passenger;
    private LocalDateTime dateTimeBooked;

    static {
        idCounter = Database.getIdCounter("Booking");
    }

    public Booking(Flight flight, User user, Passenger passenger) {
        this.id = idCounter++;
        this.flight = flight;
        this.user = user;
        this.passenger = passenger;
        this.dateTimeBooked = LocalDateTime.now();
    }

    @Override
    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public User getUser() {
        return user;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return String.join(" || ",
                String.valueOf(id),
                passenger.toString(),
                flight.getFlightDesignator(),
                flight.getFrom().toString(),
                flight.getTo().toString(),
                dateTimeBooked.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
