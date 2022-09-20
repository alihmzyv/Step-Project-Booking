package database.services;

import database.dao.DAO;
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

    public FlightService(DAO<Flight> dao) {
        this.dao = dao;
    }

    public void saveFlight(Flight flight) {
        dao.save(flight);
    }

    public Optional<Flight> getFlight(int id) {
        return dao.get(id);
    }

    public Optional<Flight> getFlight(Flight flight) {
        return dao.get(flight);
    }

    public Optional<List<Flight>> getFlights(Predicate<Flight> filter) {
        if (isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                getAllFlights().get().stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
    public Optional<List<Flight>> getAllFlights() {
        return dao.getAll();
    }

    public void setAllFlightsTo(List<Flight> flights) {
        dao.setAll(flights);
    }
    public void updateAllFlights() {
        if (isPresent()) {
            List<Flight> updatedFlights = getAllFlights().orElseGet(ArrayList::new).stream()
                    .map(flight -> {
                        if (flight.getDateTimeOfDeparture().compareTo(LocalDateTime.now()) <= 0 ||
                                flight.getCapacity() == 0) {
                            return Flight.getRandom(1, 168, ChronoUnit.HOURS);
                        }
                        return flight;
                    })
                    .collect(Collectors.toList());
            setAllFlightsTo(updatedFlights);
        }
    }

    public boolean incrementCapacity(Flight which) {
        if (isEmpty()) {
            throw new NonInstantiatedDaoException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<Flight> flights = getAllFlights().get();
        if (!flights.contains(which)) {
            throw new NoSuchFlightException(String.format("There is no such flight in database: %s", which));
        }
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .get()
                .incrementCapacity();
        setAllFlightsTo(flights);
        return true;
    }

    public boolean decrementCapacity(Flight which) {
        if (isEmpty()) {
            throw new NonInstantiatedDaoException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<Flight> flights = getAllFlights().get();
        if (!flights.contains(which)) {
            throw new NoSuchFlightException(String.format("There is no such flight in database: %s", which));
        }
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .get()
                .decrementCapacity();
        setAllFlightsTo(flights);
        return true;
    }

    public boolean addPassenger(Flight which, Passenger whom) {
        if (isEmpty()) {
            throw new NonInstantiatedDaoException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<Flight> flights = getAllFlights().get();
        if (!flights.contains(which)) {
            throw new NoSuchFlightException(String.format("There is no such flight in database: %s", which));
        }
        flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .get()
                .addPassenger(whom);
        setAllFlightsTo(flights);
        return true;
    }

    public boolean removePassenger(Flight which, Passenger whom) {
        if (isEmpty()) {
            throw new NonInstantiatedDaoException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<Flight> flights = getAllFlights().get();
        if (!flights.contains(which)) {
            throw new NoSuchFlightException(String.format("There is no such flight in database: %s", which));
        }
        if (flights.stream()
                .filter(flight -> flight.equals(which))
                .findAny()
                .get()
                .removePassenger(whom)) {
            setAllFlightsTo(flights);
            return true;
        }
        else {
            return false;
        }
    }
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

    public void displayFlights (Duration withinNext, Console console) {
        if (isEmpty()) {
            System.out.println("THERE IS NO FLIGHT AT ALL.");
            return;
        }
        List<Flight> flightsWithinNext = getFlights(flight ->
                flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now().plus(withinNext))).get();
        if (flightsWithinNext.isEmpty()) {
            System.out.println("THERE IS NO FLIGHT MATCHING YOUR INPUT.");
            return;
        }
        console.printAsTable(
                String.format("ALL AVAILABLE FLIGHTS WITHIN NEXT %s", Helper.getHumanReadableDuration(withinNext)),
                List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING", "FLIGHT DURATION"),
                flightsWithinNext,
                125);
    }

    public void displayFlights(Predicate<Flight> filter, Console console) {
        if (isEmpty()) {
            System.out.println("THERE IS NO FLIGHT AT ALL.");
            return;
        }
        List<Flight> flightsFiltered = getFlights(filter).get();
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
    public int getMaxId() {
        return dao.getMaxId();
    }
}
