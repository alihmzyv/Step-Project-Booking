package entities;

import database.Database;
import database.dao.Identifiable;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Flight implements Identifiable, Serializable{
    @Serial
    private static final long serialVersionUID = 9203702769697625107L;
    private static int idCounter;
    private final int id;
    private String flightDesignator;
    private LocalDateTime dateTimeOfDeparture;
    private LocalDateTime dateTimeOfLanding;
    private Duration flightDuration;
    private Airlines airline;
    private Airport from;
    private Airport to;
    private int capacity;
    private List<Passenger> passengers;

    static {
        idCounter = Database.getIdCounter("Flight");
    }

    public Flight(LocalDateTime dateTimeOfDeparture, LocalDateTime dateTimeOfLanding, Airlines airline, Airport from, Airport to, int capacity) {
        this.id = idCounter++;
        this.dateTimeOfDeparture = dateTimeOfDeparture;
        this.dateTimeOfLanding = dateTimeOfLanding;
        this.flightDuration = Duration.between(dateTimeOfDeparture, dateTimeOfLanding);
        this.airline = airline;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flightDesignator = getDesignator();
        this.passengers = new ArrayList<>();
    }


    @Override
    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
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
        Random rnd = new Random();
        LocalDateTime dateTimeOfDeparture = LocalDateTime.now().plusHours(rnd.nextInt(1, 24));
        LocalDateTime dateTimeOfLanding = dateTimeOfDeparture.plusMinutes(rnd.nextInt(180, 420));
        Airlines airline = Airlines.values()[rnd.nextInt(Airlines.values().length)];
        Airport from = Airport.values()[rnd.nextInt(Airport.values().length)];
        Airport[] airportsWithoutFrom = Arrays.stream(Airport.values())
                .filter(airport -> !airport.equals(from))
                .toArray(Airport[]::new);
        Airport to = airportsWithoutFrom[rnd.nextInt(airportsWithoutFrom.length)];
        int capacity = rnd.nextInt(1, 101);
        return new Flight(dateTimeOfDeparture,
                dateTimeOfLanding,
                airline,
                from,
                to,
                capacity);
    }

    public static List<Flight> getFlights(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getFlight())
                .collect(Collectors.toList());
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

    public String getDesignator() {
        return String.format("%s%d",
                this.airline.getIATADesignator(), Math.abs(Objects.hash(dateTimeOfDeparture,
                        dateTimeOfLanding,
                        airline,
                        from,
                        to,
                        capacity))).substring(0, 6);
    }

    public String getHumanReadableDuration(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    @Override
    public String toString() {
        return String.join(" || ", flightDesignator,
                from.toString(), to.toString(),
                dateTimeOfDeparture.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                dateTimeOfLanding.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                getHumanReadableDuration(flightDuration));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
