package database.services.dao_in_memory;

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
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        fs.saveBooking(booking);
        assertEquals(booking, fs.getBooking(booking).get());
    }

    @Test
    void getBookingWithIdTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertTrue(fs.getBooking(booking.getId()).isEmpty());
    }

    @Test
    void getBookingWithIdTest2() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        fs.saveBooking(booking);
        assertEquals(Optional.of(booking), fs.getBooking(booking.getId()));
    }

    @Test
    void getBookingWithObjTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertTrue(fs.getBooking(booking).isEmpty());
    }

    @Test
    void getBookingWithObjTest2() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        fs.saveBooking(booking);
        assertEquals(booking, fs.getBooking(booking).get());
    }

    @Test
    void saveAllBookingsTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.saveAllBookings(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), fs.getAllBookings());
    }

    @Test
    void getAllBookingsTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertEquals(bookings, fs.getAllBookings().get());
    }

    @Test
    void setAllBookingsTo() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), fs.getAllBookings());
    }

    @Test
    void removeBookingWithIdTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertFalse(fs.removeBooking(booking.getId()));
    }

    @Test
    void removeBookingWithIdTest2() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        fs.saveBooking(booking);
        assertTrue(fs.removeBooking(booking.getId()));
    }

    @Test
    void removeBookingWithObjTest1() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertFalse(fs.removeBooking(booking));
    }

    @Test
    void removeBookingWithObjTest2() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        fs.saveBooking(booking);
        assertTrue(fs.removeBooking(booking));
    }

    @Test
    void getMaxId() {
        BookingService fs = new BookingService(new DaoBookingInMemory(bookings));
        assertEquals(fs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }
}