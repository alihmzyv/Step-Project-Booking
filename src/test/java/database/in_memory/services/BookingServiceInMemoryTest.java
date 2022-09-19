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

    List<Booking> bookings = Booking.getRandom(100);
    Booking booking = Booking.getRandom();

    @Test
    void saveBookingTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        bs.saveBooking(booking);
        assertEquals(booking, bs.getBooking(booking).get());
    }

    @Test
    void getBookingWithIdTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertTrue(bs.getBooking(booking.getId()).isEmpty());
    }

    @Test
    void getBookingWithIdTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        bs.saveBooking(booking);
        assertEquals(Optional.of(booking), bs.getBooking(booking.getId()));
    }

    @Test
    void getBookingWithObjTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertTrue(bs.getBooking(booking).isEmpty());
    }

    @Test
    void getBookingWithObjTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        bs.saveBooking(booking);
        assertEquals(booking, bs.getBooking(booking).get());
    }

    @Test
    void saveAllBookingsTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.saveAllBookings(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), bs.getAllBookings());
    }

    @Test
    void getAllBookingsTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertEquals(bookings, bs.getAllBookings().get());
    }

    @Test
    void setAllBookingsTo() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bs.getAllBookings());
    }

    @Test
    void removeBookingWithIdTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertFalse(bs.removeBooking(booking.getId()));
    }

    @Test
    void removeBookingWithIdTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        bs.saveBooking(booking);
        assertTrue(bs.removeBooking(booking.getId()));
    }

    @Test
    void removeBookingWithObjTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertFalse(bs.removeBooking(booking));
    }

    @Test
    void removeBookingWithObjTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        bs.saveBooking(booking);
        assertTrue(bs.removeBooking(booking));
    }

    @Test
    void getMaxId() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        assertEquals(bs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bs.getMaxId());
    }
}