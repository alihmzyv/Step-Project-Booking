package database.in_memory.dao;

import database.dao.DaoBookingInMemory;
import database.dao.DaoBookingInMemory;
import entities.Booking;
import entities.Booking;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoBookingInMemoryTest {

    private final List<Booking> randomBookings = Booking.getRandom(100);
    private final Booking randomBooking = Booking.getRandom();

    @Test
    void getAllTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertEquals(Optional.of(randomBookings), daoBookingInMemory.getAll());
    }

    @Test
    void saveAllTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        List<Booking> randomBookings2 = Booking.getRandom(100);
        daoBookingInMemory.saveAll(randomBookings2);
        List<Booking> allBookings = new ArrayList<>(randomBookings);
        allBookings.addAll(randomBookings2);
        assertEquals(Optional.of(allBookings), daoBookingInMemory.getAll());
    }

    @Test
    void setAllTo() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        List<Booking> randomBookings2 = Booking.getRandom(100);
        daoBookingInMemory.setAllTo(randomBookings2);
        assertEquals(Optional.of(randomBookings2), daoBookingInMemory.getAll());
    }

    @Test
    void isPresentTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertTrue(daoBookingInMemory.isPresent());
    }

    @Test
    void isEmptyTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertFalse(daoBookingInMemory.isEmpty());
    }

    @Test
    void saveTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        daoBookingInMemory.save(randomBooking);
        assertTrue(daoBookingInMemory.isPresent());
        assertTrue(daoBookingInMemory.getAll().get().stream()
                .anyMatch(flight -> flight.equals(randomBooking)));
    }

    @Test
    void getWithIdTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertEquals(Optional.empty(), daoBookingInMemory.get(randomBooking.getId()));
    }

    @Test
    void getWithIdTest2() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        daoBookingInMemory.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingInMemory.get(randomBooking.getId()));
    }

    @Test
    void getWithObjTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertEquals(Optional.empty(), daoBookingInMemory.get(randomBooking));
    }

    @Test
    void getWithObjTest2() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        daoBookingInMemory.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingInMemory.get(randomBooking));
    }

    @Test
    void removeWithIdTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertFalse(daoBookingInMemory.remove(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest2() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        daoBookingInMemory.save(randomBooking);
        assertTrue(daoBookingInMemory.remove(randomBooking.getId()));
    }

    @Test
    void removeWithObjTest1() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertFalse(daoBookingInMemory.remove(randomBooking));
    }

    @Test
    void removeWithObjTest2() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        daoBookingInMemory.save(randomBooking);
        assertTrue(daoBookingInMemory.remove(randomBooking));
    }

    @Test
    void getMaxId() {
        DaoBookingInMemory daoBookingInMemory = new DaoBookingInMemory(randomBookings);
        assertTrue(daoBookingInMemory.isPresent());
        assertEquals(daoBookingInMemory.getAll().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), daoBookingInMemory.getMaxId());
    }
}