package database.services;

import database.dao.DAO;
import entities.Flight;
import entities.Helper;
import io.Console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FlightService {
    private DAO<Flight> dao;

    public FlightService(DAO<Flight> dao) {
        this.dao = dao;
    }

    public FlightService() {
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

    public void saveAllFlights(List<Flight> flights) {
        dao.saveAll(flights);
    }
    public Optional<List<Flight>> getAllFlights() {
        return dao.getAll();
    }

    public void setAllFlights(List<Flight> flights) {
        dao.setAll(flights);
    }
    public void updateAllFlights() {
        List<Flight> updatedFlights = getAllFlights().orElseGet(ArrayList::new).stream()
                .map(flight -> {
                    if (flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now()) ||
                            flight.getCapacity() == 0) {
                        return Flight.getRandom();
                    }
                    return flight;
                })
                .collect(Collectors.toList());
        dao.setAll(updatedFlights);
    }
    public boolean removeFlight(int id) {
        return dao.remove(id);
    }
    public boolean removeFlight(Flight flight) {
        return dao.remove(flight);
    }
    public void displayFlight(int flightId, Console console) {
        Optional<Flight> flightOptional = getFlight(flightId);
        if (flightOptional.isEmpty()) {
            System.out.printf("There is no flight with id: %d\n", flightId);
            return;
        }
        Flight flightFound = flightOptional.get();
        List fields = List.of(flightFound.getFlightDesignator(),
                flightFound.getId(),
                flightFound.getFrom(),
                flightFound.getTo(),
                flightFound.getDateTimeOfDeparture(),
                flightFound.getDateTimeOfLanding(),
                Helper.getHumanReadableDuration(flightFound.getFlightDuration()),
                flightFound.getCapacity());
        console.printInRow(
                List.of("Flight", "ID", "From", "To", "Time of Departure", "Time of Landing",
                        "Flight Duration", "Capacity"),
                fields,
                115);
    }
    public void displayAllFlights(Console console) {
        if (isEmpty()) {
            System.out.println("There is no flight at all.");
            return;
        }
        console.printAsTable(
                List.of("Flight", "ID", "From", "To", "Time of Departure", "Time of Landing", "Flight Duration"),
                getAllFlights().orElseGet(ArrayList::new),
                110);
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
