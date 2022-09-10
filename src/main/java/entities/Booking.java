package entities;

import database.dao.Identifiable;

import java.awt.print.Book;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Booking implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = -2078227854687969036L;
    private static int idCounter = 1;


    private final int id;
    private Flight flight;
    private User user;
    private Passenger passenger;
    private LocalDateTime dateTimeBooked;

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
        return  String.format("%s %s %s %s",
                        id,
                        flight.getFlightDesignator() +
                        passenger +
                        dateTimeBooked);
    }
}
