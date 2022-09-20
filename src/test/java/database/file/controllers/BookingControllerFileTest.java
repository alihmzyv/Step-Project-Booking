package database.file.controllers;

import database.controllers.BookingController;
import database.dao.*;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerFileTest {
    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    List<User> users = User.getRandom(100);
    List<Passenger> passengers = Passenger.getRandom(100);
    List<Booking> bookings = IntStream.range(0, 100)
            .mapToObj(i -> new Booking(users.get(i), flights.get(i), passengers.get(i)))
            .collect(Collectors.toCollection(ArrayList::new));
    private final File flightsFile = new File("src/test/java/database/file/files/flights.bin");
    private final File usersFile = new File("src/test/java/database/file/files/users.bin");
    private final File bookingsFile = new File("src/test/java/database/file/files/bookings.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFlightsFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(flightsFile))) {
            oos.writeObject(flights);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeFlightsEmpty() {
        try {
            new FileOutputStream(flightsFile).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeUsersFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeUsersEmpty() {
        try {
            new FileOutputStream(usersFile).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeBookingsFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(bookingsFile))) {
            oos.writeObject(bookings);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeBookingsEmpty() {
        try {
            new FileOutputStream(bookingsFile).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeAllFull() {
        makeFlightsFull();
        makeUsersFull();
        makeBookingsFull();
    }

    private void makeAllEmpty() {
        makeFlightsEmpty();
        makeUsersEmpty();
        makeBookingsEmpty();
    }

    @Test
    void getAllBookingsTest1() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertEquals(Optional.of(bookings), bc.getAllBookings());
    }

    @Test
    void getAllBookingsTest2() {
        makeAllEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertEquals(Optional.empty(), bc.getAllBookings());
    }

    @Test
    void getAllBookingsTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                bc::getAllBookings);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllBookingsToTest1() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
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
    void setAllBookingsToTest2() {
        makeAllEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
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
    void setAllBookingsToTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        List<Booking> bookings2 = Booking.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bc.setAllBookingsTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveTest1() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        User user = randomBooking.getUser();
        Flight flight = randomBooking.getFlight();
        int capacityBeforeBooking = flight.getCapacity();
        Passenger passenger = randomBooking.getPassenger();
        bc.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bc.getBooking(randomBooking));
        assertTrue(us.getUser(user).get().hasBooking(randomBooking));
        assertEquals(fs.getFlight(flight).get().getCapacity(), capacityBeforeBooking - 1);
        assertTrue(fs.getFlight(flight).get().hasPassenger(passenger));
        
    }

    @Test
    void saveTest2() {
        makeAllEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        Booking randomBooking = Booking.getRandom();
        User user = randomBooking.getUser();
        Flight flight = randomBooking.getFlight();
        int capacityBeforeBooking = flight.getCapacity();
        Passenger passenger = randomBooking.getPassenger();
        bc.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bc.getBooking(randomBooking));
        assertTrue(us.getUser(user).get().hasBooking(randomBooking));
        assertEquals(fs.getFlight(flight).get().getCapacity(), capacityBeforeBooking - 1);
        assertTrue(fs.getFlight(flight).get().hasPassenger(passenger));
    }

    @Test
    void saveTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bc.saveBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertFalse(bc.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithIdTest2() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
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
        int capacityAfterSaveBeforeRemove = fs.getFlight(flight).get().getCapacity();
        assertTrue(bc.removeBooking(randomBooking));
        assertEquals(Optional.empty(), bc.getBooking(randomBooking));
        assertFalse(us.getUser(user).get().hasBooking(randomBooking));
        assertEquals(capacityAfterSaveBeforeRemove + 1, fs.getFlight(flight).get().getCapacity());
        assertFalse(fs.getFlight(flight).get().hasPassenger(passenger));
    }

    @Test
    void removeWithIdTest3() {
        makeAllEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> bc.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithIdTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bc.removeBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeAllFull();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
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

    @Test
    void getMaxIdTest2() {
        makeAllEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(bookingsFile));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        assertTrue(bc.isEmpty());
        assertEquals(1, bc.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        UserService us = new UserService(new DaoUserFile(usersFile));
        FlightService fs = new FlightService(new DaoFlightFile(flightsFile));
        BookingController bc = new BookingController(
                bs,
                us,
                fs
        );
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                bc::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}