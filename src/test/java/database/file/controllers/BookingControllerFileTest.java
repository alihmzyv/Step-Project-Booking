package database.file.controllers;

import database.controllers.BookingController;
import database.dao.*;
import database.services.BookingService;
import database.services.FlightService;
import database.services.PassengerService;
import database.services.UserService;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerFileTest {
    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    List<User> users = User.getRandom(100);
    List<Passenger> passengers = Passenger.getRandom(100);
    List<Booking> bookings = Booking.getRandom(100);
    Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
    User user = User.getRandom();
    Passenger passenger = Passenger.getRandom();
    Booking booking = new Booking(user, flight, passenger);
    private final File flightsFile = new File("src/test/java/database/file/files/flights.bin");
    private final File usersFile = new File("src/test/java/database/file/files/users.bin");
    private final File passengersFile = new File("src/test/java/database/file/files/passengers.bin");
    private final File bookingsFile = new File("src/test/java/database/file/files/bookings.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFlightsFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(flightsFile))) {
            oos.writeObject(flights);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeFlightsEmpty() {
        try {
            new FileOutputStream(flightsFile).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeUsersFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeUsersEmpty() {
        try {
            new FileOutputStream(usersFile).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makePassengersFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(passengersFile))) {
            oos.writeObject(passengers);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makePassengersEmpty() {
        try {
            new FileOutputStream(passengersFile).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeBookingsFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(bookingsFile))) {
            oos.writeObject(bookings);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeBookingsEmpty() {
        try {
            new FileOutputStream(bookingsFile).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeAllFull() {
        makeFlightsFull();
        makeUsersFull();
        makePassengersFull();
        makeBookingsFull();
    }

    private void makeAllEmpty() {
        makeFlightsEmpty();
        makeUsersEmpty();
        makePassengersEmpty();
        makeBookingsEmpty();
    }

    @Test
    void getAllBookingsTest1() {
        makeAllFull();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertEquals(Optional.of(bookings), bc.getAllBookings());
    }

    @Test
    void getAllBookingsTest2() {
        makeAllEmpty();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertEquals(Optional.empty(), bc.getAllBookings());
    }

    @Test
    void getAllBookingsTest3() {
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                bc::getAllBookings);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllBookingsToTest1() {
        makeAllFull();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bc.getAllBookings());
    }

    @Test
    void setAllBookingsToTest2() {
        makeAllEmpty();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bc.getAllBookings());
    }

    @Test
    void setAllBookingsToTest3() {
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bc.setAllBookingsTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveTest1() {
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
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
    void saveTest2() {
        makeAllEmpty();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        int capacityBeforeBooking = fs.getFlight(flight).get().getCapacity();
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
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
    void saveTest3() {
        makeAllFull();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(usersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bc.saveBooking(booking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeAllFull();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(usersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertEquals(Optional.empty(), bc.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeAllFull();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(usersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        bc.saveBooking(booking);
        assertEquals(Optional.of(booking), bc.getBooking(booking.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeAllEmpty();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(usersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> bc.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        makeAllEmpty();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(usersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bc.getBooking(Booking.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeAllFull();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertFalse(bc.removeBooking(booking));
    }

    @Test
    void removeWithIdTest2() {
        makeAllFull();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
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
    void removeWithIdTest3() {
        makeAllEmpty();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> bc.removeBooking(booking));
    }

    @Test
    void removeWithIdTest4() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bc.removeBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllBookingsTest1() {
        makeAllFull();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
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
    void saveAllBookingsTest2() {
        makeAllEmpty();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        bc.saveAllBookings(bookings2);
        assertEquals(Optional.of(bookings2), bc.getAllBookings());
    }

    @Test
    void saveAllBookingsTest3() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bc.saveAllBookings(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeAllFull();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertEquals(bc.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bc.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeAllEmpty();
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(bookingsFile)),
                us,
                fs,
                ps);
        assertEquals(1, bc.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        User user = User.getRandom();
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        Booking booking = new Booking(user, flight, passenger);
        UserService us = new UserService(new DaoUserFile(usersFile));
        us.saveUser(user);
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        fs.saveFlight(flight);
        PassengerService ps = new PassengerService(new DaoPassengerFile(passengersFile));
        ps.savePassenger(passenger);
        BookingController bc = new BookingController(
                new BookingService(new DaoBookingFile(fileNonExisting)),
                us,
                fs,
                ps);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                bc::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}