package database.dao.dao_in_memory;

import database.dao.DaoPassengerInMemory;
import entities.Passenger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoPassengerInMemoryTest {

    private final List<Passenger> randomPassengers = Passenger.getRandom(100);
    private final Passenger randomPassenger = Passenger.getRandom();

    @Test
    void saveTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        daoPassengerInMemory.save(randomPassenger);
        assertEquals(randomPassenger, daoPassengerInMemory.get(randomPassenger).get());
    }


    @Test
    void getWithIdTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertTrue(daoPassengerInMemory.get(randomPassenger.getId()).isEmpty());
    }

    @Test
    void getWithIdTest2() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        daoPassengerInMemory.save(randomPassenger);
        assertEquals(Optional.of(randomPassenger), daoPassengerInMemory.get(randomPassenger.getId()));
    }

    @Test
    void getWithObjTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertTrue(daoPassengerInMemory.get(randomPassenger).isEmpty());
    }

    @Test
    void getWithObjTest2() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        daoPassengerInMemory.save(randomPassenger);
        assertEquals(randomPassenger, daoPassengerInMemory.get(randomPassenger).get());
    }

    @Test
    void removeWithIdTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertFalse(daoPassengerInMemory.remove(randomPassenger.getId()));
    }

    @Test
    void removeWithIdTest2() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        daoPassengerInMemory.save(randomPassenger);
        assertTrue(daoPassengerInMemory.remove(randomPassenger.getId()));
    }

    @Test
    void removeWithObjTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertFalse(daoPassengerInMemory.remove(randomPassenger));
    }

    @Test
    void removeWithObjTest2() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        daoPassengerInMemory.save(randomPassenger);
        assertTrue(daoPassengerInMemory.remove(randomPassenger));
    }

    @Test
    void getAllTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertEquals(randomPassengers, daoPassengerInMemory.getAll().get());
    }

    @Test
    void saveAllTest1() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        List<Passenger> randomPassengers2 = Passenger.getRandom(100);
        daoPassengerInMemory.saveAll(randomPassengers2);
        List<Passenger> allPassengers = new ArrayList<>();
        allPassengers.addAll(randomPassengers);
        allPassengers.addAll(randomPassengers2);
        assertEquals(Optional.of(allPassengers), daoPassengerInMemory.getAll());
    }

    @Test
    void setAllTo() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        List<Passenger> randomPassengers2 = Passenger.getRandom(100);
        daoPassengerInMemory.setAllTo(randomPassengers2);
        assertEquals(Optional.of(randomPassengers2), daoPassengerInMemory.getAll());
    }

    @Test
    void getMaxId() {
        DaoPassengerInMemory daoPassengerInMemory = new DaoPassengerInMemory(randomPassengers);
        assertEquals(daoPassengerInMemory.getAll().get().stream()
                .mapToInt(Passenger::getId)
                .max()
                .getAsInt(), daoPassengerInMemory.getMaxId());
    }
}