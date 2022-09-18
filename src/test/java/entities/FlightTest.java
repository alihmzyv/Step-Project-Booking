package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.Predicate;

class FlightTest {


    @Test
    void getRandomTest1() {
        assertTrue(getPredicate(168, ChronoUnit.HOURS).test(Flight.getRandom(0, 168, ChronoUnit.HOURS)));
    }

    @Test
    void getRandomManyTest1() {
        assertTrue(Flight.getRandom(1000_000, 0, 168, ChronoUnit.HOURS).stream().allMatch(getPredicate(168, ChronoUnit.HOURS)));
    }

    private Predicate<Flight> getPredicate (int latest, ChronoUnit unit) {
        return flight ->
                flight.getDateTimeOfDeparture().compareTo(LocalDateTime.now().plus(Duration.of(latest, unit))) < 0 &&
                flight.getDateTimeOfLanding().compareTo(flight.getDateTimeOfDeparture().plus(Duration.ofMinutes(60))) >= 0 &&
                flight.getDateTimeOfLanding().compareTo(flight.getDateTimeOfDeparture().plus(Duration.ofMinutes(300))) <= 0 &&
                Arrays.stream(Airline.values()).anyMatch(airline -> airline.equals(flight.getAirline())) &&
                Arrays.stream(Airport.values()).anyMatch(airport -> airport.equals(flight.getFrom())) &&
                Arrays.stream(Airport.values()).anyMatch(airport -> airport.equals(flight.getTo())) &&
                flight.getCapacity() >= 30 && flight.getCapacity() < 101;
    }
}