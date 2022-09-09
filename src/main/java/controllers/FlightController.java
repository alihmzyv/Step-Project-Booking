package controllers;

import entities.Flight;
import services.FlightService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class FlightController {
    private FlightService fs;

    public FlightController(FlightService fs) {
        this.fs = fs;
    }

    public FlightController() {
        fs = new FlightService();
    }

    public FlightService getService() {
        return fs;
    }

    public List<Flight> getAllFlights() {
        return fs.getAllFlights();
    }

    public void displayAllFlights() {
        fs.displayAllFlights();
    }

    public void updateAllFlights() {
        fs.getAllFlights().stream()
                .filter(flight -> flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now()))
                .forEach(flight ->
                        flight.setDateTimeOfDeparture(LocalDateTime.now().plusHours(new Random().nextInt(24))));
    }
}