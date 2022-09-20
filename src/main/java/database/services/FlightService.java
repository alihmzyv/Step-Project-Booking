package database.services;

import database.dao.DAO;
import database.dao.DaoFile;
import entities.Flight;
import entities.Passenger;
import exceptions.database_exceptions.NoSuchFlightException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import helpers.Helper;
import io.Console;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightService {
    private final DAO<Flight> dao;


    //constructors
    public FlightService(DAO<Flight> dao) {
        this.dao = dao;
    }


    //methods
    public void saveFlight(Flight flight) {
        dao.save(flight);
    }

    public Optional<Flight> getFlight(int id) {
        return dao.get(id);
    }

    public Optional<Flight> getFlight(Flight flight) {
        return dao.get(flight);
    }

    /*
     * Returns the list of flights in the database that matches the given predicate
     * * filter - the predicate against which flights will be tested
     * * returns an empty list if there is no flight matching the filter.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public List<Flight> getFlights(Predicate<Flight> filter) {
        requiresNonNull();
        return getAllFlights().get().stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<List<Flight>> getAllFlights() {
        return dao.getAll();
    }

    public void setAllFlights(List<Flight> flights) {
        dao.setAll(flights);
    }

    /*
     * Replaces the flights in the database whose:
     * * capacity is zero or the dateTimeOfDeparture is already before than the current date-time
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public void updateAllFlights() {
        requiresNonNull();
        List<Flight> updatedFlights = getAllFlights().get().stream()
                .map(flight -> {
                    if (flight.getDateTimeOfDeparture().compareTo(LocalDateTime.now()) <= 0 ||
                            flight.getCapacity() == 0) {
                        return Flight.getRandom(1, 168, ChronoUnit.HOURS);
                    }
                    return flight;
                    })
                .collect(Collectors.toList());
        setAllFlights(updatedFlights);
    }

    /*
     * Increases the capacity of the given flight by 1.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     * * throws NoSuchFlightException if the database does not contain the given flight
     */
    public void incrementCapacity(Flight which) {
        requiresNonNull();
        List<Flight> flights = getAllFlights().get();
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchFlightException(String.format("There is no such flight in database: %s", which)))
                .incrementCapacity();
        setAllFlights(flights);
    }

    /*
     * Decreases the capacity of the given flight by 1.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     * * throws NoSuchFlightException if the database does not contain the given flight
     */
    public void decrementCapacity(Flight which) {
        requiresNonNull();
        List<Flight> flights = getAllFlights().get();
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchFlightException(String.format("There is no such flight in database: %s", which)))
                .decrementCapacity();
        setAllFlights(flights);
    }

    /*
     * Adds the given passenger to the passengers list of the given flight.
     * * which - the Flight to which Passenger to be added
     * * whom - the Passenger to be added
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     * * throws NoSuchFlightException if the database does not contain the given flight
     */
    public void addPassenger(Flight which, Passenger whom) {
        requiresNonNull();
        List<Flight> flights = getAllFlights().get();
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchFlightException(String.format("There is no such flight in database: %s", which)))
                .addPassenger(whom);
        setAllFlights(flights);
    }

    /*
     * If the given flight contains the passenger, removes the passenger from the passengers list of flight,
     * and returns true, otherwise returns false
     * * which - the Flight from which the passenger should be removed
     * * whom - the Passenger to be removed
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     * * throws NoSuchFlightException if the database does not contain the given flight
     */
    public boolean removePassenger(Flight which, Passenger whom) {
        requiresNonNull();
        List<Flight> flights = getAllFlights().get();
        if (flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .orElseThrow(() -> new NoSuchFlightException(String.format("There is no such flight in database: %s", which)))
                .removePassenger(whom)) {
            setAllFlights(flights);
            return true;
        }
        return false;
    }

    /*
     * Displays the flight in the database whose id matches the given id,
     * otherwise if there is no such flight, displays "No Flight" message.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public void displayFlight(int flightId, Console console) {
        Optional<Flight> flightOpt = getFlight(flightId);
        if (flightOpt.isEmpty()) {
            console.printf("THERE IS NO FLIGHT WITH ID: %d\n", flightId);
            return;
        }
        console.printAsRow(
                "FLIGHT",
                List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING",
                        "FLIGHT DURATION", "CAPACITY"),
                List.of(flightOpt.get(), flightOpt.get().getCapacity()),
                130);
    }

    /*
     * Displays the flights whose dateTimeOfDeparture is before the current date-time with added the given duration,
     * if there is no such a flight in the database, display "No Flight" message.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public void displayFlights (Duration withinNext, Console console) {
        Predicate<Flight> filter = flight ->
                flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now().plus(withinNext));
        displayFlights(filter, console);
    }

    /*
     * Displays the flights which is matches the given predicate,
     * if there is no such a flight in the database, display "No Flight" message.
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public void displayFlights(Predicate<Flight> filter, Console console) {
        List<Flight> flightsFiltered = getFlights(filter);
        if (flightsFiltered.isEmpty()) {
            System.out.println("THERE IS NO FLIGHT MATCHING YOUR INPUT.");
            return;
        }
        console.printAsTable(
                "ALL FLIGHTS MATCHING",
                List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING", "FLIGHT DURATION"),
                flightsFiltered,
                125
        );
    }

    public boolean isPresent() {
        return dao.isPresent();
    }
    public boolean isEmpty() {
        return dao.isEmpty();
    }
    public void requiresNonNull() {
        dao.requiresNonNull();
    }
    public int getMaxId() {
        return dao.getMaxId();
    }
}
