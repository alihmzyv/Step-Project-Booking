package entities;

import database.Database;
import helpers.Helper;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Flight implements Identifiable, Serializable{
    @Serial
    private static final long serialVersionUID = 9203702769697625107L;
    private static int idCounter;

    private final int id;
    private final String flightDesignator;
    private final Airline airline;
    private final Airport from;
    private final Airport to;
    private LocalDateTime dateTimeOfDeparture;
    private final LocalDateTime dateTimeOfLanding;
    private final Duration flightDuration;
    private int capacity;
    private final List<Passenger> passengers;


    static {
        idCounter = Database.getIdCounter("Flight");
    }


    //constructors
    public Flight(Airline airline, Airport from, Airport to,
                  LocalDateTime dateTimeOfDeparture, LocalDateTime dateTimeOfLanding,
                  int capacity) {
        this.id = idCounter++;
        this.airline = airline;
        this.from = from;
        this.to = to;
        this.dateTimeOfDeparture = dateTimeOfDeparture;
        this.dateTimeOfLanding = dateTimeOfLanding;
        this.flightDuration = Duration.between(dateTimeOfDeparture, dateTimeOfLanding);
        this.capacity = capacity;
        this.flightDesignator = generateDesignator();
        this.passengers = new ArrayList<>();
    }


    //getter and setters
    @Override
    public int getId() {
        return id;
    }

    public LocalDateTime getDateTimeOfDeparture() {
        return dateTimeOfDeparture;
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDate getDateOfDeparture() {
        return dateTimeOfDeparture.toLocalDate();
    }

    public LocalDateTime getDateTimeOfLanding() {
        return dateTimeOfLanding;
    }

    public Airline getAirline() {
        return airline;
    }

    public Duration getFlightDuration() {
        return flightDuration;
    }

    public void setDateTimeOfDeparture(LocalDateTime dateTimeOfDeparture) {
        this.dateTimeOfDeparture = dateTimeOfDeparture;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }


    //methods
    //static methods
    public static Flight getRandom(int earliest, int latest, ChronoUnit unit) {
        Random rnd = new Random();
        LocalDateTime dateTimeOfDeparture = LocalDateTime.now().plus(Duration.of(rnd.nextInt(earliest, latest + 1), unit));
        LocalDateTime dateTimeOfLanding = dateTimeOfDeparture.plus(Duration.ofMinutes(rnd.nextInt(60, 301)));
        Airline airline = Airline.getRandom();
        Airport from = Airport.getRandom();
        Airport to = Airport.getRandomExcept(from);
        int capacity = rnd.nextInt(30, 101);
        return new Flight(airline, from, to, dateTimeOfDeparture, dateTimeOfLanding, capacity);
    }

    public static List<Flight> getRandom(int count, int earliest, int latest, ChronoUnit unit) {
        return IntStream.range(0, count)
                .mapToObj(i -> getRandom(earliest, latest, unit))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //instance methods
    public void decrementCapacity() {
        setCapacity(this.capacity - 1);
    }

    public void incrementCapacity() {
        setCapacity(this.capacity + 1);
    }

    public String generateDesignator() {
        return String.format("%s%d", this.airline.getIATADesignator(),
                        Math.abs(Objects.hash(from, to, capacity)))
                .substring(0, 6);
    }

    @Override
    public String toString() {
        return String.join(" || ",
                String.valueOf(id),
                flightDesignator,
                from.toString(), to.toString(),
                dateTimeOfDeparture.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                dateTimeOfLanding.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                Helper.getHumanReadableDuration(flightDuration));
    }

    //equals and hashcode
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

    public boolean containsPassenger(Passenger passenger) {
        return passengers.contains(passenger);
    }
}
