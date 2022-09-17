package database.controllers;

import entities.Flight;
import io.Console;
import database.services.FlightService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightController {
    private final FlightService fs;

    public FlightController(FlightService fs) {
        this.fs = fs;
    }

    public FlightService getService() {
        return fs;
    }

    public Optional<Flight> getFlight(int id) {
        return fs.getFlight(id);
    }

    public Optional<Flight> getFlight(Flight flight) {
        return fs.getFlight(flight);
    }

    public Optional<List<Flight>> getAllFlights() {
        return fs.getAllFlights();
    }

    public void setAllFlightsTo(List<Flight> flights) {
        fs.setAllFlightsTo(flights);
    }

    public void displayAllFlights(Console console) {
        fs.displayAllFlights(console);
    }

    public void displayFlight(int flightId, Console console) {
        fs.displayFlight(flightId, console);
    }

    public void displayFlights(Duration withinNext, Console console) {
        fs.displayFlights(withinNext, console);
    }

    public void displayFlights(Predicate<Flight> filter, Console console) {
        fs.displayFlights(filter, console);
    }

    public boolean removeFlight(int id) {
        return fs.removeFlight(id);
    }

    public boolean removeFlight(Flight flight) {
        return fs.removeFlight(flight);
    }

    public void updateAllFlights() {
        fs.updateAllFlights();
    }

    public boolean isPresent() {
        return fs.isPresent();
    }
    public boolean isEmpty() {
        return fs.isEmpty();
    }
    public int getMaxId() {
        return fs.getMaxId();
    }
}
