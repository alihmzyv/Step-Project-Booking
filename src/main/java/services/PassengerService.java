package services;

import dao.DAO;
import entities.Passenger;

import java.util.List;

public class PassengerService {
    private DAO<Passenger> dao;

    public PassengerService(DAO<Passenger> dao) {
        this.dao = dao;
    }

    public PassengerService() {
    }
    public List<Passenger> getAllPassengers() {
        return dao.getAll();
    }

    public void updateAll() {
        throw new RuntimeException("not imple");
    }
}
