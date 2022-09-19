package database.in_memory.controllers;

import database.controllers.PassengerController;
import database.dao.DaoPassengerInMemory;
import database.services.PassengerService;
import entities.Passenger;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengerControllerInMemoryTest {
    List<Passenger> passengers = Passenger.getRandom(100);
    Passenger passenger = Passenger.getRandom();

    @Test
    void savePassengerTest1() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        pc.savePassenger(passenger);
        assertEquals(passenger, pc.getPassenger(passenger).get());
    }

    @Test
    void getPassengerWithIdTest1() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        assertTrue(pc.getPassenger(passenger.getId()).isEmpty());
    }

    @Test
    void getPassengerWithIdTest2() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        pc.savePassenger(passenger);
        assertEquals(Optional.of(passenger), pc.getPassenger(passenger.getId()));
    }

    @Test
    void getPassengerWithObjTest1() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        assertTrue(pc.getPassenger(passenger).isEmpty());
    }

    @Test
    void getPassengerWithObjTest2() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        pc.savePassenger(passenger);
        assertEquals(passenger, pc.getPassenger(passenger).get());
    }

    @Test
    void getAllPassengersTest1() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        assertEquals(passengers, pc.getAllPassengers().get());
    }

    @Test
    void setAllPassengersTo() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        pc.setAllPassengersTo(passengers2);
        assertEquals(Optional.of(passengers2), pc.getAllPassengers());
    }

    @Test
    void getMaxId() {
        PassengerController pc = new PassengerController(new PassengerService(new DaoPassengerInMemory(passengers)));
        assertEquals(pc.getAllPassengers().get().stream()
                .mapToInt(Passenger::getId)
                .max()
                .getAsInt(), pc.getMaxId());
    }
}