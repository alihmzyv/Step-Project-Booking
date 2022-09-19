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
    void saveTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertEquals(randomFlight, daoFlightInMemory.get(randomFlight).get());
    }


    @Test
    void getWithIdTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertTrue(daoFlightInMemory.get(randomFlight.getId()).isEmpty());
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
        assertTrue(daoFlightInMemory.get(randomFlight).isEmpty());
    }

    @Test
    void getWithObjTest2() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        daoFlightInMemory.save(randomFlight);
        assertEquals(randomFlight, daoFlightInMemory.get(randomFlight).get());
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
    void getAllTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertEquals(randomFlights, daoFlightInMemory.getAll().get());
    }

    @Test
    void saveAllTest1() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        List<Flight> randomFlights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightInMemory.saveAll(randomFlights2);
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(randomFlights);
        allFlights.addAll(randomFlights2);
        assertEquals(Optional.of(allFlights), daoFlightInMemory.getAll());
    }

    @Test
    void setAllTo() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        List<Flight> randomFlights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightInMemory.setAllTo(randomFlights2);
        assertEquals(Optional.of(randomFlights2), daoFlightInMemory.getAll());
    }

    @Test
    void getMaxId() {
        DaoFlightInMemory daoFlightInMemory = new DaoFlightInMemory(randomFlights);
        assertEquals(daoFlightInMemory.getAll().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), daoFlightInMemory.getMaxId());
    }
}