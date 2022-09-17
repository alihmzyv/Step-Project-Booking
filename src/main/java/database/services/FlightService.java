package database.services;

import database.dao.DAO;
import entities.Flight;
import entities.Passenger;
import helpers.Helper;
import io.Console;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public void saveAllFlights(List<Flight> flights) {
        dao.saveAll(flights);
    }
    public Optional<List<Flight>> getAllFlights() {
        return dao.getAll();
    }

    public void setAllFlightsTo(List<Flight> flights) {
        dao.setAllTo(flights);
    }
    public void updateAllFlights() {
        if (isPresent()) {
            List<Flight> updatedFlights = getAllFlights().orElseGet(ArrayList::new).stream()
                    .map(flight -> {
                        if (flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now()) ||
                                flight.getCapacity() == 0) {
                            return Flight.getRandom();
                        }
                        return flight;
                    })
                    .collect(Collectors.toList());
            setAllFlightsTo(updatedFlights);
        }
    }
    public boolean removeFlight(int id) {
        return dao.remove(id);
    }
    public boolean removeFlight(Flight flight) {
        return dao.remove(flight);
    }
    public void displayFlight(int flightId, Console console) {
        Optional<Flight> flightOpt = getFlight(flightId);
        if (flightOpt.isEmpty()) {
            console.printf("THERE IS NO FLIGHT WITH ID: %d\n", flightId);
            return;
        }
        console.printAsTable(
                "FLIGHT",
                List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING",
                        "FLIGHT DURATION", "CAPACITY"),
                List.of(flightOpt.get()),
                130);
    }
    public void displayAllFlights(Console console) {
        if (isEmpty()) {
            console.println("THERE IS NO FLIGHT AT ALL.");
            return;
        }
        console.printAsTable(
                "ALL AVAILABLE FLIGHTS",
                List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING", "FLIGHT DURATION"),
                getAllFlights().get(),
                125);
    }

    public void displayFlights (Duration withinNext, Console console) {
        if (isEmpty()) {
            System.out.println("THERE IS NO FLIGHT AT ALL.");
            return;
        }
        List<Flight> flightsWithinNext = getFlights(flight ->
                flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now().plus(withinNext))).get();
        if (flightsWithinNext.isEmpty()) {
            System.out.println("THERE IS NO FLIGHT AT ALL.");
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
            System.out.println("THERE IS NO FLIGHT AT ALL.");
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
