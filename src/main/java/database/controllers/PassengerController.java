package database.controllers;

import entities.Passenger;
import database.services.PassengerService;

import java.util.List;
import java.util.Optional;

public class PassengerController {
    private final PassengerService ps;

    public PassengerController(PassengerService ps) {
        this.ps = ps;
    }

    public PassengerService getService() {
        return ps;
    }

    public Optional<Passenger> getPassenger(int id) {
        return ps.getPassenger(id);
    }

    public Optional<Passenger> getPassenger(Passenger passenger) {
        return ps.getPassenger(passenger);
    }

    public Optional<List<Passenger>> getAllPassengers() {
        return ps.getAllPassengers();
    }

    public void setAllPassengersTo(List<Passenger> data) {
        ps.setAllPassengersTo(data);
    }
    public int getMaxId() {
        return ps.getMaxId();
    }
}
