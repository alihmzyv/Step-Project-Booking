package controllers;

import entities.Flight;
import services.FlightService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

    public Optional<List<Flight>> getAllFlights() {
        return fs.getAllFlights();
    }

    public void setAllFlights(List<Flight> flights) {
        fs.setAllFlights(flights);
    }

    public void displayAllFlights() {
        fs.displayAllFlights();
    }

    public void updateAllFlights() {
        List<Flight> updatedFlights = getAllFlights().orElseGet(ArrayList::new)
                .stream()
                .filter(flight -> flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now()) ||
                        flight.getCapacity() == 0)
                .map(flight -> Flight.getFlight())
                .collect(Collectors.toList());

        fs.setAllFlights(updatedFlights);
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
