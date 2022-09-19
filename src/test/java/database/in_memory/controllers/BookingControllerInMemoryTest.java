package database.in_memory.controllers;

import database.controllers.BookingController;
import database.dao.DaoBookingInMemory;
import database.dao.DaoFlightInMemory;
import database.dao.DaoPassengerInMemory;
import database.dao.DaoUserInMemory;
import database.services.BookingService;
import database.services.FlightService;
import database.services.PassengerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BookingControllerInMemoryTest {

    List<Booking> bookings = Booking.getRandom(100);
    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    List<User> users = User.getRandom(100);
    List<Passenger> passengers = Passenger.getRandom(100);

    @Test
    void saveBooking() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        bc.saveBooking(booking);
        assertEquals(Optional.of(booking), bc.getBooking(booking));
        assertTrue(us.getUser(user).get().hasBooking(booking));
        assertEquals(fs.getFlight(flight).get().getCapacity(), capacityBeforeBooking - 1);
        assertTrue(fs.getFlight(flight).get().containsPassenger(passenger));
        assertTrue(ps.contains(passenger));
    }

    @Test
    void getBookingWithIdTest1() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        assertTrue(bc.getBooking(booking.getId()).isEmpty());
    }

    @Test
    void getBookingWithIdTest2() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        bc.saveBooking(booking);
        assertEquals(Optional.of(booking), bc.getBooking(booking.getId()));
    }

    @Test
    void getBookingWithObjTest1() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        assertTrue(bc.getBooking(booking).isEmpty());
    }

    @Test
    void getBookingWithObjTest2() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        bc.saveBooking(booking);
        assertEquals(booking, bc.getBooking(booking).get());
    }

    @Test
    void saveAllBookings() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.saveAllBookings(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), bc.getAllBookings());
    }

    @Test
    void getAllBookingsTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        assertEquals(bookings, bc.getAllBookings().get());
    }

    @Test
    void setAllBookingsTo() {
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bc.getAllBookings());
    }

    @Test
    void removeBookingTest1() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        int capacityBeforeRemoving = fs.getFlight(flight).get().getCapacity();
        assertFalse(bc.removeBooking(booking));
        assertEquals(fs.getFlight(flight).get().getCapacity(), capacityBeforeRemoving);
    }

    @Test
    void removeBookingTest2() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        bc.saveBooking(booking);
        int capacityBeforeRemoving = fs.getFlight(flight).get().getCapacity();
        assertTrue(bc.removeBooking(booking));
        assertEquals(Optional.empty(), bc.getBooking(booking));
        assertFalse(us.getUser(user).get().hasBooking(booking));
        assertEquals(fs.getFlight(flight).get().getCapacity(), capacityBeforeRemoving + 1);
        assertFalse(fs.getFlight(flight).get().containsPassenger(passenger));
    }

    @Test
    void getMaxId() {
        UserService us = new UserService(new DaoUserInMemory(users));
        FlightService fs = new FlightService(new DaoFlightInMemory(flights));
        PassengerService ps = new PassengerService(new DaoPassengerInMemory(passengers));
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingInMemory(bookings)),
                us,
                fs,
                ps);
        assertEquals(bc.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bc.getMaxId());
    }
}