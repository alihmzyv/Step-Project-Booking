package database.in_memory.services;

import database.dao.DaoFlightInMemory;
import database.services.FlightService;
import entities.Flight;
import entities.Passenger;
import exceptions.database_exceptions.NoSuchFlightException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceInMemoryTest {

    List<Flight> randomFlights = Flight.getRandom(10, 1, 168, ChronoUnit.HOURS);

    @Test
    void getAllFlightsTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertEquals(Optional.of(randomFlights), fs.getAllFlights());
    }

    @Test
    void setAllFlightsToTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        List<Flight> randomFlights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        fs.setAllFlightsTo(randomFlights2);
        assertEquals(Optional.of(randomFlights2), fs.getAllFlights());
    }

    @Test
    void isPresent() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertTrue(fs.isPresent());
    }

    @Test
    void isEmpty() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertFalse(fs.isEmpty());
    }

    @Test
    void saveFlightTest1() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        fs.saveFlight(randomFlight);
        assertTrue(fs.isPresent());
        assertTrue(fs.getAllFlights().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void getFlightWithIdTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertEquals(Optional.empty(), fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getFlightWithIdTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight.getId()));
    }

    @Test
    void getFlightWithObjTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertEquals(Optional.empty(), fs.getFlight(randomFlight));
    }

    @Test
    void getFlightWithObjTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        fs.saveFlight(randomFlight);
        assertEquals(Optional.of(randomFlight), fs.getFlight(randomFlight));
    }

    @Test
    void updateAllFlightsTest1() {
        List<Flight> randomFlightsCopy = new ArrayList<>(randomFlights);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFlight.setDateTimeOfDeparture(LocalDateTime.now()); //already before than LocalDateTime.now(), thus outdated
        randomFlightsCopy.add(randomFlight);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlightsCopy));
        fs.updateAllFlights();
        assertNotEquals(Optional.of(randomFlightsCopy), fs.getAllFlights());
    }

    @Test
    void updateAllFlightsTest2() {
        List<Flight> randomFlightsCopy = new ArrayList<>(randomFlights);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        randomFlight.setCapacity(0); //sold out
        randomFlightsCopy.add(randomFlight);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlightsCopy));
        fs.updateAllFlights();
        assertNotEquals(Optional.of(randomFlightsCopy), fs.getAllFlights());
    }

    @Test
    void incrementCapacityTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        fs.saveFlight(randomFlight);
        int capacityBefore = fs.getFlight(randomFlight).get().getCapacity();
        fs.incrementCapacity(randomFlight);
        assertEquals(capacityBefore + 1, fs.getFlight(randomFlight).get().getCapacity());
    }

    @Test
    void incrementCapacityTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.incrementCapacity(randomFlight));
    }

    @Test
    void decrementCapacityTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        fs.saveFlight(randomFlight);
        int capacityBefore = fs.getFlight(randomFlight).get().getCapacity();
        fs.decrementCapacity(randomFlight);
        assertEquals(capacityBefore - 1, fs.getFlight(randomFlight).get().getCapacity());
    }

    @Test
    void decrementCapacityTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.decrementCapacity(randomFlight));
    }

    @Test
    void addPassengerTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        fs.saveFlight(randomFlight);
        fs.addPassenger(randomFlight, passenger);
        assertTrue(fs.getFlight(randomFlight).get().getPassengers().contains(passenger));
    }

    @Test
    void addPassengerTest2() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.addPassenger(randomFlight, passenger));
    }

    @Test
    void removePassengerTest1() {
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        Passenger passenger = Passenger.getRandom();
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
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
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertThrowsExactly(NoSuchFlightException.class, () -> fs.removePassenger(randomFlight, passenger));
    }

    @Test
    void getMaxId() {
        FlightService fs = new FlightService(new DaoFlightInMemory(randomFlights));
        assertTrue(fs.isPresent());
        assertEquals(fs.getAllFlights().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }
}