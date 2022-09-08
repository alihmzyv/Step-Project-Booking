package entities;

import dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Flight implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = 9203702769697625107L;
    private static int idCounter = 1;
    private final int id;
    private String flightDesignator;
    private LocalDate dateOfDeparture;
    private LocalTime timeOfDeparture;
    private Airport from;
    private Airport to;
    private int capacity;
    private List<Passenger> passengers;

    public Flight(int id, String flightDesignator, LocalDateTime dateTime, Airport from, Airport to, int capacity, List<Passenger> passengers) {
        this.id = idCounter++;
        this.flightDesignator = flightDesignator;
        this.dateOfDeparture = dateTime.toLocalDate();
        this.timeOfDeparture = dateTime.toLocalTime();
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.passengers = passengers;
    }

    @Override
    public int getId() {
        return id;
    }

    public static Flight getFlight() {
        throw new RuntimeException("not implemented");
    }
}
