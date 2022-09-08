package controllers;

import entities.Flight;
import services.FlightService;

import java.util.List;

public class FlightController {
    private FlightService fs;

    public FlightController(FlightService fs) {
        this.fs = fs;
    }

    public FlightController() {
        fs = new FlightService();
    }

    public List<Flight> getAllFlights() {
        return fs.getAllFlights();
    }

    public void updateAllFlights() {
        fs.updateAll();
    }
}
