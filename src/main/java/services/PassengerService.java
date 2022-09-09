package services;

import database.dao.DAO;
import entities.Passenger;

import java.util.List;

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
    public void getPassenger(int id) {
        dao.get(id);
    }
    public void saveAllPassengers(List<Passenger> passengers) {
        dao.saveAll(passengers);
    }
    public List<Passenger> getAllPassengers() {
        return dao.getAll();
    }

}
