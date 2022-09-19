package database.controllers.file;

import database.controllers.FlightController;
import database.dao.DaoFlightFile;
import database.services.FlightService;
import entities.Flight;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerFileTest {

    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
    private final File file = new File("src/test/java/database/services/dao_file/flight/flights.bin");
    private final File fileNonExisting = new File("src/test/java/database/services/dao_file/flight/none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(flights);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    @Test
    void saveFlightTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        fc.saveFlight(flight);
        List<Flight> flightsCopy = new ArrayList<>(flights);
        flightsCopy.add(flight);
        assertEquals(Optional.of(flightsCopy), fc.getAllFlights());
    }

    @Test
    void saveFlightTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        fc.saveFlight(flight);
        assertEquals(Optional.of(List.of(flight)), fc.getAllFlights());
    }

    @Test
    void saveFlightTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.saveFlight(flight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void getWithIdTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.empty(), fc.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fc.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fc.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.empty(), fc.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
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
        assertEquals(Optional.empty(), fc.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void getWithObjTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getAllFlightsTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(Optional.of(flights), fc.getAllFlights());
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.setAllFlightsTo(flights2));
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
    void removeWithIdTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fc.saveFlight(randomFlight);
        assertTrue(fc.removeFlight(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fc.saveFlight(randomFlight);
        assertTrue(fc.removeFlight(randomFlight));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertFalse(fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest4() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fc.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(fc.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fc.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(file)));
        assertEquals(1, fc.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        FlightController fc = new FlightController(new FlightService(new DaoFlightFile(fileNonExisting)));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fc::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}