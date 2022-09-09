package controllers;

import entities.Passenger;
import services.PassengerService;

import java.util.List;

public class PassengerController {
    private PassengerService ps;

    public PassengerController(PassengerService ps) {
        this.ps = ps;
    }

    public PassengerController() {
        ps = new PassengerService();
    }

    public PassengerService getService() {
        return ps;
    }

    public List<Passenger> getAllPassengers() {
        return ps.getAllPassengers();
    }
}
