package entities;

import database.Database;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public Booking(User user, Flight flight, Passenger passenger) {
        this.flight = flight;
        this.user = user;
        this.passenger = passenger;
        this.id = idCounter++;
        this.dateTimeBooked = LocalDateTime.now();
    }

    public static Booking getRandom() {
        return new Booking(User.getRandom(), Flight.getRandom(1, 168, ChronoUnit.HOURS), Passenger.getRandom());
    }
    public static List<Booking> getRandom(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> getRandom())
                .collect(Collectors.toCollection(ArrayList::new));
    }


    //getter and setters
    @Override
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
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
                flight.getDateTimeOfDeparture().
                        format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)),
                dateTimeBooked.
                        format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)));
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
