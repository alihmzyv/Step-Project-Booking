package controllers;

import entities.Passenger;
import services.PassengerService;

import java.util.List;

public class PassengerController {
    private PassengerService fs;

    public PassengerController(PassengerService fs) {
        this.fs = fs;
    }

    public PassengerController() {
        fs = new PassengerService();
    }

    public List<Passenger> getAllPassengers() {
        return fs.getAllPassengers();
    }

    public void updateAllPassengers() {
        fs.updateAll();
    }
}
