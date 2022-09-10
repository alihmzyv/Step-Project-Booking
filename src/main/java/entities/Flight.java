package entities;

import database.dao.Identifiable;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class Flight implements Identifiable, Serializable{
    @Serial
    private static final long serialVersionUID = 9203702769697625107L;
    private static int idCounter = 1;
    private final int id;
    private String flightDesignator;
    private LocalDateTime dateTimeOfDeparture;
    private LocalDateTime dateTimeOfLanding;
    private Duration flightDuration;
    private Airport from;
    private Airport to;
    private int capacity;
    private List<Passenger> passengers;

    public Flight(String flightDesignator, LocalDateTime dateTimeOfDeparture, LocalDateTime dateTimeOfLanding, Airport from, Airport to, int capacity, List<Passenger> passengers) {
        this.id = idCounter++;
        this.flightDesignator = flightDesignator;
        this.dateTimeOfDeparture = dateTimeOfDeparture;
        this.dateTimeOfLanding = dateTimeOfLanding;
        this.flightDuration = Duration.between(dateTimeOfLanding, dateTimeOfDeparture);
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.passengers = passengers;
    }

    @Override
    public int getId() {
        return id;
    }

    public LocalDateTime getDateTimeOfDeparture() {
        return dateTimeOfDeparture;
    }

    public void setDateTimeOfDeparture(LocalDateTime dateTimeOfDeparture) {
        this.dateTimeOfDeparture = dateTimeOfDeparture;
    }

    public String getFlightDesignator() {
        return flightDesignator;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDate getDate() {
        return dateTimeOfDeparture.toLocalDate();
    }

    public Duration getFlightDuration() {
        return flightDuration;
    }

    public static Flight getFlight() {
        throw new RuntimeException("not implemented");
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void decrementCapacity() {
        setCapacity(getCapacity() - 1);
    }

    public void incrementCapacity() {
        setCapacity(getCapacity() + 1);
    }

    @Override
    public String toString() {
        return String.join(" || ", String.valueOf(id), flightDesignator,
                from.toString(), to.toString(),
                dateTimeOfDeparture.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                dateTimeOfLanding.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                flightDuration.toString());
    }
}
