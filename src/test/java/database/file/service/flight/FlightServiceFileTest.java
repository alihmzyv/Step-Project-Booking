package database.file.service.flight;

import database.dao.DaoFlightFile;
import database.services.FlightService;
import entities.Flight;
import entities.Passenger;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NoSuchFlightException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceFileTest {

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
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.of(randomFlights), fs.getAllFlights());
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
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
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
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.setAllFlightsTo(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertTrue(fs.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.isPresent());
    }

    @Test
    void isPresentTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertFalse(fs.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertTrue(fs.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        assertTrue(fs.isPresent());
        assertTrue(fs.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        assertTrue(fs.isPresent());
        assertTrue(fs.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.saveFlight(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getWithIdTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.getFlight(randomFlight.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertEquals(Optional.empty(), fs.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> fs.getFlight(randomFlight));
    }

    @Test
    void getWithObjTest4() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> fs.getFlight(randomFlight));
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
    void incrementCapacityTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(flight);
        int capacityBefore = fs.getFlight(flight).get().getCapacity();
        fs.incrementCapacity(flight);
        assertEquals(capacityBefore + 1, fs.getFlight(flight).get().getCapacity());
    }

    @Test
    void incrementCapacityTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.incrementCapacity(flight));
    }

    @Test
    void decrementCapacityTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(flight);
        int capacityBefore = fs.getFlight(flight).get().getCapacity();
        fs.incrementCapacity(flight);
        assertEquals(capacityBefore + 1, fs.getFlight(flight).get().getCapacity());
    }

    @Test
    void decrementCapacityTest2() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        Flight flight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.incrementCapacity(flight));
    }

    @Test
    void addPassengerTest1() {
        makeFull();
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        fs.addPassenger(randomFlight, passenger);
        assertTrue(fs.getFlight(randomFlight).get().getPassengers().contains(passenger));
    }

    @Test
    void addPassengerTest2() {
        makeFull();
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.addPassenger(randomFlight, passenger));
    }

    @Test
    void removePassengerTest1() {
        makeFull();
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        fs.saveFlight(randomFlight);
        fs.addPassenger(randomFlight, passenger);
        assertTrue(fs.getFlight(randomFlight).get().getPassengers().contains(passenger));
        fs.removePassenger(randomFlight, passenger);
        assertFalse(fs.getFlight(randomFlight).get().getPassengers().contains(passenger));
    }

    @Test
    void removePassengerTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.removePassenger(randomFlight, passenger));
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertTrue(fs.isPresent());
        assertEquals(fs.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        FlightService fs = new FlightService(new DaoFlightFile(file));
        assertTrue(fs.isEmpty());
        assertEquals(1, fs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        FlightService fs = new FlightService(new DaoFlightFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                fs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}