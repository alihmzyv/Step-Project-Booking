package database.services.dao_file.flight;

import database.dao.DaoFlightFile;
import database.dao.DaoFlightInMemory;
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

class FlightServiceFileTest {

    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
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
    void getAllFlightsTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.of(flights), fs.getAllFlights());
    }

    @Test
    void getAllFlightsTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getAllFlights());
    }

    @Test
    void getAllFlightsTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getAllFlights);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllFlightsToTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fs.getAllFlights());
    }

    @Test
    void setAllFlightsToTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.setAllFlightsTo(flights2);
        assertEquals(Optional.of(flights2), fs.getAllFlights());
    }

    @Test
    void setAllFlightsToTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.setAllFlightsTo(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        List<Flight> flightsCopy = new ArrayList<>(flights);
        flightsCopy.add(randomFlight);
        assertEquals(Optional.of(flightsCopy), fs.getAllFlights());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(List.of(randomFlight)), fs.getAllFlights());
    }

    @Test
    void saveTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveFlight(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        assertEquals(Optional.empty(), fs.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void getWithObjTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertTrue(fs.removeFlight(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertTrue(fs.removeFlight(randomFlight));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeFlight(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void updateAllFlightsTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now()
        List<Flight> flightsCopy = new ArrayList<>(fs.getAllFlights().get());
        flightsCopy.add(flight);
        fs.setAllFlightsTo(flightsCopy);
        fs.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fs.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        flight.setCapacity(0); //already before than LocalDateTime.now()
        List<Flight> flightsCopy = new ArrayList<>(fs.getAllFlights().get());
        flightsCopy.add(flight);
        fs.setAllFlightsTo(flightsCopy);
        fs.updateAllFlights();
        assertNotEquals(Optional.of(flightsCopy), fs.getAllFlights());
    }

    @Test
    void saveAllFlightsTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.saveAllFlights(flights2);
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(flights);
        allFlights.addAll(flights2);
        assertEquals(Optional.of(allFlights), fs.getAllFlights());
    }

    @Test
    void saveAllFlightsTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.saveAllFlights(flights2);
        assertEquals(Optional.of(flights2), fs.getAllFlights());
    }

    @Test
    void saveAllFlightsTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveAllFlights(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(fs.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(1, fs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}