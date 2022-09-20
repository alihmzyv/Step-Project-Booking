package database.in_memory.services;

import database.dao.DaoBookingInMemory;
import database.services.BookingService;
import entities.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceInMemoryTest {

    List<Booking> randomBookings = Booking.getRandom(100);
    Booking randomBooking = Booking.getRandom();

    @Test
    void getAllBookingsTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        assertEquals(Optional.of(randomBookings), bs.getAllBookings());
    }

    @Test
    void setAllBookingsTo() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        List<Booking> randomBookings2 = Booking.getRandom(100);
        bs.setAllBookingsTo(randomBookings2);
        assertEquals(Optional.of(randomBookings2), bs.getAllBookings());
    }

    @Test
    void saveBookingTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        bs.saveBooking(randomBooking);
        assertTrue(bs.isPresent());
        assertTrue(bs.getAllBookings().get().stream()
                .anyMatch(booking -> booking.equals(randomBooking)));
    }

    @Test
    void getBookingWithObjTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        assertEquals(Optional.empty(), bs.getBooking(randomBooking));
    }

    @Test
    void getBookingWithObjTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        bs.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bs.getBooking(randomBooking));
    }

    @Test
    void removeBookingWithObjTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        assertFalse(bs.removeBooking(randomBooking));
    }

    @Test
    void removeBookingWithObjTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        bs.saveBooking(randomBooking);
        assertTrue(bs.removeBooking(randomBooking));
    }

    @Test
    void getMaxId() {
        BookingService bs = new BookingService(new DaoBookingInMemory(randomBookings));
        assertTrue(bs.isPresent());
        assertEquals(bs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bs.getMaxId());
    }
}