package database.in_memory.controllers;

import database.controllers.FlightController;
import database.dao.DaoFlightInMemory;
import database.services.FlightService;
import entities.Flight;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerInMemoryTest {

    List<Flight> randomFligths = Flight.getRandom(10, 1, 168, ChronoUnit.HOURS);
    Flight randomFligth = Flight.getRandom(1, 168, ChronoUnit.HOURS);

    @Test
    void getAllFlightsTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertEquals(randomFligths, fc.getAllFlights().get());
    }

    @Test
    void setAllFlightsTo() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        List<Flight> randomFligths2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fc.setAllFlightsTo(randomFligths2);
        assertEquals(Optional.of(randomFligths2), fc.getAllFlights());
    }

    @Test
    void isPresent() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertTrue(fc.isPresent());
    }

    @Test
    void isEmpty() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertFalse(fc.isEmpty());
    }

    @Test
    void saveFlightTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        fc.saveFlight(randomFligth);
        assertTrue(fc.isPresent());
        assertTrue(fc.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFligth)));
    }

    @Test
    void getFlightWithIdTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertEquals(Optional.empty(), fc.getFlight(randomFligth.getId()));
    }

    @Test
    void getFlightWithIdTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        fc.saveFlight(randomFligth);
        assertEquals(Optional.of(randomFligth), fc.getFlight(randomFligth.getId()));
    }

    @Test
    void getFlightWithObjTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertEquals(Optional.empty(), fc.getFlight(randomFligth));
    }

    @Test
    void getFlightWithObjTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        fc.saveFlight(randomFligth);
        assertEquals(Optional.of(randomFligth), fc.getFlight(randomFligth));
    }

    @Test
    void updateAllFlightsTest1() {
        List<Flight> randomFligthsCopy = new ArrayList<>(randomFligths);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFlight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now(), thus outdated
        randomFligthsCopy.add(randomFlight);
        FlightService fc = new FlightService(new DaoFlightInMemory(randomFligthsCopy));
        fc.updateAllFlights();
        assertNotEquals(Optional.of(randomFligthsCopy), fc.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        List<Flight> randomFligthsCopy = new ArrayList<>(randomFligths);
        Flight randomFligth = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFligth.setCapacity(0); //sold out
        randomFligthsCopy.add(randomFligth);
        FlightService fc = new FlightService(new DaoFlightInMemory(randomFligthsCopy));
        fc.updateAllFlights();
        assertNotEquals(Optional.of(randomFligthsCopy), fc.getAllFlights());
    }

    @Test
    void getMaxId() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(randomFligths)));
        assertTrue(fc.isPresent());
        assertEquals(fc.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fc.getMaxId());
    }
}