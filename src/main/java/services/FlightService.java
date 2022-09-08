package services;

import dao.DAO;
import entities.Flight;

import java.util.List;

public class FlightService {
    private DAO<Flight> dao;

    public FlightService(DAO<Flight> dao) {
        this.dao = dao;
    }

    public FlightService() {
    }
    public List<Flight> getAllFlights() {
        return dao.getAll();
    }

    public void updateAll() {
        throw new RuntimeException("not imple");
    }
}
