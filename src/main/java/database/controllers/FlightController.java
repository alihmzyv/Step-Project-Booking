package database.controllers;

import entities.Flight;
import io.Console;
import database.services.FlightService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class FlightController {
    private final FlightService fs;

    public FlightController(FlightService fs) {
        this.fs = fs;
    }

    public FlightController() {
        fs = new FlightService();
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

    public void setAllFlights(List<Flight> flights) {
        fs.setAllFlights(flights);
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

    public void displayFlight(Flight flight, Console console) {
        fs.displayFlight(flight.getId(), console);
    }
}
