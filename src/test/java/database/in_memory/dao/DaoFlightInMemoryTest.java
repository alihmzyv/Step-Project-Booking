package database.in_memory.dao;

import database.dao.DaoFlightInMemory;
import entities.Flight;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoFlightInMemoryTest {

    private final List<Flight> randomFlights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    private final Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);

    @Test
    void getAllTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertEquals(Optional.of(randomFlights), daoFlightInMemory.getAll());
    }

    @Test
    void saveAllTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        List<Flight> randomFlights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightInMemory.saveAll(randomFlights2);
        List<Flight> allFlights = new ArrayList<>(randomFlights);
        allFlights.addAll(randomFlights2);
        assertEquals(Optional.of(allFlights), daoFlightInMemory.getAll());
    }

    @Test
    void setAllTo() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        List<Flight> randomFlights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightInMemory.setAll(randomFlights2);
        assertEquals(Optional.of(randomFlights2), daoFlightInMemory.getAll());
    }

    @Test
    void isPresentTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertTrue(daoFlightInMemory.isPresent());
    }

    @Test
    void isEmptyTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertFalse(daoFlightInMemory.isEmpty());
    }

    @Test
    void saveTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertTrue(daoFlightInMemory.isPresent());
        assertTrue(daoFlightInMemory.getAll().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void getWithIdTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertEquals(Optional.empty(), daoFlightInMemory.get(randomFlight.getId()));
    }

    @Test
    void getWithIdTest2() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightInMemory.get(randomFlight.getId()));
    }

    @Test
    void getWithObjTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertEquals(Optional.empty(), daoFlightInMemory.get(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightInMemory.get(randomFlight));
    }

    @Test
    void removeWithIdTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertFalse(daoFlightInMemory.remove(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest2() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertTrue(daoFlightInMemory.remove(randomFlight.getId()));
    }

    @Test
    void removeWithObjTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertFalse(daoFlightInMemory.remove(randomFlight));
    }

    @Test
    void removeWithObjTest2() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertTrue(daoFlightInMemory.remove(randomFlight));
    }

    @Test
    void getMaxId() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertTrue(daoFlightInMemory.isPresent());
        assertEquals(daoFlightInMemory.getAll().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), daoFlightInMemory.getMaxId());
    }
}