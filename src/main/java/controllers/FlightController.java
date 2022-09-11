package controllers;

import entities.Flight;
import services.FlightService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public boolean removeFlight(int id) {
        Optional<Flight> flightOptional = getFlight(id);
        if (flightOptional.isEmpty()) {
            return false;
        }
        return fs.removeFlight(flightOptional.get());
    }

    public boolean removeFlight(Flight flight) {
        Optional<Flight> flightOptional = getFlight(flight);
        if (flightOptional.isEmpty()) {
            return false;
        }
        return fs.removeFlight(flightOptional.get());
    }

    public void displayAllFlights() {
        fs.displayAllFlights();
    }

    public void updateAllFlights() {
        List<Flight> updatedFlights = getAllFlights().get()
                .stream()
                .map(flight -> {
                    if (flight.getDateTimeOfDeparture().isBefore(LocalDateTime.now()) ||
                            flight.getCapacity() == 0) {
                        return Flight.getRandom();
                    }
                    return flight;
                })
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
