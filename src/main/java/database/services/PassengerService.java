package database.services;

import database.dao.DAO;
import entities.Passenger;

import java.util.List;
import java.util.Optional;

public class PassengerService {
    private DAO<Passenger> dao;

    public PassengerService(DAO<Passenger> dao) {
        this.dao = dao;
    }

    public PassengerService() {
    }

    public void savePassenger(Passenger passenger) {
        dao.save(passenger);
    }
    public Optional<Passenger> getPassenger(int id) {
        return dao.get(id);
    }

    public Optional<Passenger> getPassenger(Passenger passenger) {
        return dao.get(passenger);
    }

    public void saveAllPassengers(List<Passenger> passengers) {
        dao.saveAll(passengers);
    }
    public Optional<List<Passenger>> getAllPassengers() {
        return dao.getAll();
    }

    public void setAllPassengers(List<Passenger> data) {
        dao.setAll(data);
    }

    public boolean removePassenger(int id) {
        return dao.remove(id);
    }
    public boolean removePassenger(Passenger passenger) {
        return dao.remove(passenger);
    }
    public int getMaxId() {
        return dao.getMaxId();
    }
}
