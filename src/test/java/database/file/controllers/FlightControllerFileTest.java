package database.file.controllers;

import database.controllers.FlightController;
import database.dao.DaoFlightFile;
import database.services.FlightService;
import entities.Flight;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerFileTest {

    List<Flight> randomFlights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
    private final File file = new File("src/test/java/database/file/files/flights.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomFlights);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    @Test
    void getAllFlightsTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.of(randomFlights), fc.getAllFlights());
    }

    @Test
    void getAllFlightsTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.empty(), fc.getAllFlights());
    }

    @Test
    void getAllFlightsTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                fc::getAllFlights);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllFlightsToTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fc.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fc.getAllFlights());
    }

    @Test
    void setAllFlightsToTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fc.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fc.getAllFlights());
    }

    @Test
    void setAllFlightsToTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.setAllFlightsTo(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertTrue(fc.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.isPresent());
    }

    @Test
    void isPresentTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertTrue(fc.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveFlightTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        fc.saveFlight(randomFlight);
        assertTrue(fc.isPresent());
        assertTrue(fc.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveFlightTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        fc.saveFlight(randomFlight);
        assertTrue(fc.isPresent());
        assertTrue(fc.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveFlightTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.saveFlight(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void getWithIdTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.empty(), fc.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        fc.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fc.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> fc.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.getFlight(randomFlight.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.empty(), fc.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fc.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fc.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> fc.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fc.getFlight(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void updateAllFlightsTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now()
        List<Flight> flightsCopy = new ArrayList<>(fc.getAllFlights().get());
        flightsCopy.add(flight);
        fc.setAllFlightsTo(flightsCopy);
        fc.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fc.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setCapacity(0); //already before than LocalDateTime.now()
        List<Flight> flightsCopy = new ArrayList<>(fc.getAllFlights().get());
        flightsCopy.add(flight);
        fc.setAllFlightsTo(flightsCopy);
        fc.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fc.getAllFlights());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertTrue(fc.isPresent());
        assertEquals(fc.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fc.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertTrue(fc.isEmpty());
        assertEquals(1, fc.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                fc::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}