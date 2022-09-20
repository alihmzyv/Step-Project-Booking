package database.in_memory.controllers;

import database.controllers.BookingController;
import database.dao.DaoBookingInMemory;
import database.dao.DaoFlightInMemory;
import database.dao.DaoUserInMemory;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BookingControllerInMemoryTest {

    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    List<User> users = User.getRandom(100);
    List<Passenger> passengers = Passenger.getRandom(100);
    List<Booking> bookings = IntStream.range(0, 100)
            .mapToObj(i -> new Booking(users.get(i), flights.get(i), passengers.get(i)))
            .collect(Collectors.toCollection(ArrayList::new));

    @Test
    void getAllBookingsTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertEquals(Optional.of(bookings), bc.getAllBookings());
    }

    @Test
    void setAllBookingsTo() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bc.getAllBookings());
    }

    @Test
    void isPresentTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertTrue(bc.isPresent());
    }

    @Test
    void isEmptyTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertFalse(bc.isEmpty());
    }

    @Test
    void saveBooking() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        User user = randomBooking.getUser();
        Flight flight = randomBooking.getFlight();
        Passenger passenger = randomBooking.getPassenger();
        int capacityBeforeBooking = flight.getCapacity();
        bc.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bc.getBooking(randomBooking));
        assertTrue(us.getUser(user).get().hasBooking(randomBooking));
        assertEquals(capacityBeforeBooking - 1, fs.getFlight(flight).get().getCapacity());
        assertTrue(fs.getFlight(flight).get().hasPassenger(passenger));
    }

    @Test
    void getBookingWithObjTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        assertEquals(Optional.empty(), bc.getBooking(randomBooking));
    }

    @Test
    void getBookingWithObjTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        bc.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bc.getBooking(randomBooking));
    }

    @Test
    void removeBookingTest1() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        Flight flight = randomBooking.getFlight();
        int capacityBeforeRemoving = flight.getCapacity();
        assertFalse(bc.removeBooking(randomBooking));
        assertEquals(capacityBeforeRemoving, flight.getCapacity());
    }

    @Test
    void removeBookingTest2() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        User user = randomBooking.getUser();
        Flight flight = randomBooking.getFlight();
        Passenger passenger = randomBooking.getPassenger();
        bc.saveBooking(randomBooking);
        int capacityBeforeRemoving = flight.getCapacity();
        assertTrue(bc.removeBooking(randomBooking));
        assertEquals(Optional.empty(), bc.getBooking(randomBooking));
        assertFalse(us.getUser(user).get().hasBooking(randomBooking));
        assertEquals(capacityBeforeRemoving + 1, fs.getFlight(flight).get().getCapacity());
        assertFalse(fs.getFlight(flight).get().hasPassenger(passenger));
    }

    @Test
    void getMaxId() {
        BookingService bs = new BookingService(new DaoBookingInMemory(bookings));
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertTrue(bc.isPresent());
        assertEquals(bc.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bc.getMaxId());
    }
}