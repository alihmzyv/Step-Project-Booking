package entities;

import database.Database;
import database.dao.Identifiable;

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
    private final Flight flight;
    private final User user;
    private final Passenger passenger;
    private final LocalDateTime dateTimeBooked;


    static {
        idCounter = Database.getIdCounter("Booking");
    }


    //constructors
    public Booking(Flight flight, User user, Passenger passenger) {
        this.dateTimeBooked = LocalDateTime.now();
        this.id = idCounter++;
        this.flight = flight;
        this.user = user;
        this.passenger = passenger;
    }


    //getter and setters
    @Override
    public int getId() {
        return id;
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


    //methods
    //instance methods
    @Override
    public String toString() {
        return String.join(" || ",
                passenger.toString(),
                flight.getFlightDesignator(),
                flight.getFrom().toString(),
                flight.getTo().toString(),
                dateTimeBooked.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)));
    }

    //equals and hashcode
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
