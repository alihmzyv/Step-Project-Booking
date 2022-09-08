package entities;

import dao.Identifiable;

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

    public Booking(Flight flight, User user, Passenger passenger, LocalDateTime dateTimeBooked) {
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
}
