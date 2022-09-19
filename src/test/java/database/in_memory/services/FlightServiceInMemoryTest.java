package database.in_memory.services;

import database.dao.DaoFlightInMemory;
import database.services.FlightService;
import entities.Flight;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import io.RealConsole;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceInMemoryTest {

    List<Flight> flights = Flight.getRandom(10, 1, 168, ChronoUnit.HOURS);
    Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);

    @Test
    void saveFlightTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        assertEquals(flight, fs.getFlight(flight).get());
    }

    @Test
    void getFlightWithIdTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertTrue(fs.getFlight(flight.getId()).isEmpty());
    }

    @Test
    void getFlightWithIdTest2() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        assertEquals(Optional.of(flight), fs.getFlight(flight.getId()));
    }

    @Test
    void getFlightWithObjTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertTrue(fs.getFlight(flight).isEmpty());
    }

    @Test
    void getFlightWithObjTest2() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        assertEquals(flight, fs.getFlight(flight).get());
    }

    @Test
    void saveAllFlightsTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.saveAllFlights(flights2);
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(flights);
        allFlights.addAll(flights2);
        assertEquals(Optional.of(allFlights), fs.getAllFlights());
    }

    @Test
    void getAllFlightsTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertEquals(flights, fs.getAllFlights().get());
    }

    @Test
    void setAllFlightsTo() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fs.getAllFlights());
    }

    @Test
    void updateAllFlightsTest1() {
        List<Flight> flightsCopy = new ArrayList<>(flights);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFlight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now()
        flightsCopy.add(randomFlight);
        FlightService fs = new FlightService(new DaoFlightInMemory(flightsCopy));
        fs.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fs.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        List<Flight> flightsCopy = new ArrayList<>(flights);
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setCapacity(0);
        flightsCopy.add(flight);
        FlightService fs = new FlightService(new DaoFlightInMemory(flightsCopy));
        fs.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fs.getAllFlights());
    }

    @Test
    void removeFlightWithIdTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertFalse(fs.removeFlight(flight.getId()));
    }

    @Test
    void removeFlightWithIdTest2() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        assertTrue(fs.removeFlight(flight.getId()));
    }

    @Test
    void removeFlightWithObjTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertFalse(fs.removeFlight(flight));
    }

    @Test
    void removeFlightWithObjTest2() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        assertTrue(fs.removeFlight(flight));
    }

    @Test
    void getMaxId() {
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        assertEquals(fs.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }
}