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

    List<Flight> flights = Flight.getRandom(10, 1, 168, ChronoUnit.HOURS);
    Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);


    @Test
    void saveFlightTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        fc.saveFlight(flight);
        assertEquals(flight, fc.getFlight(flight).get());
    }

    @Test
    void getFlightWithIdTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertTrue(fc.getFlight(flight.getId()).isEmpty());
    }

    @Test
    void getFlightWithIdTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        fc.saveFlight(flight);
        assertEquals(Optional.of(flight), fc.getFlight(flight.getId()));
    }

    @Test
    void getFlightWithObjTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertTrue(fc.getFlight(flight).isEmpty());
    }

    @Test
    void getFlightWithObjTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        fc.saveFlight(flight);
        assertEquals(flight, fc.getFlight(flight).get());
    }

    @Test
    void getAllFlightsTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertEquals(flights, fc.getAllFlights().get());
    }

    @Test
    void setAllFlightsTo() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fc.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fc.getAllFlights());
    }

    @Test
    void updateAllFlightsTest1() {
        List<Flight> flightsCopy = new ArrayList<>(flights);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFlight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now()
        flightsCopy.add(randomFlight);
        FlightService fc = new FlightService(new DaoFlightInMemory(flightsCopy));
        fc.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fc.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        List<Flight> flightsCopy = new ArrayList<>(flights);
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setCapacity(0);
        flightsCopy.add(flight);
        FlightService fc = new FlightService(new DaoFlightInMemory(flightsCopy));
        fc.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fc.getAllFlights());
    }

    @Test
    void removeFlightWithIdTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertFalse(fc.removeFlight(flight.getId()));
    }

    @Test
    void removeFlightWithIdTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        fc.saveFlight(flight);
        assertTrue(fc.removeFlight(flight.getId()));
    }

    @Test
    void removeFlightWithObjTest1() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertFalse(fc.removeFlight(flight));
    }

    @Test
    void removeFlightWithObjTest2() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        fc.saveFlight(flight);
        assertTrue(fc.removeFlight(flight));
    }

    @Test
    void getMaxId() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightInMemory(flights)));
        assertEquals(fc.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fc.getMaxId());
    }
}