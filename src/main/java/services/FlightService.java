package services;

import database.dao.DAO;
import entities.Flight;
import entities.IndexedDisplayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FlightService {
    private DAO<Flight> dao;

    public FlightService(DAO<Flight> dao) {
        this.dao = dao;
    }

    public FlightService() {
    }

    public void saveFlight(Flight flight) {
        dao.save(flight);
    }

    public Optional<Flight> getFlight(int id) {
        return dao.get(id);
    }

    public void saveAllFlights(List<Flight> flights) {
        dao.saveAll(flights);
    }
    public List<Flight> getAllFlights() {
        return dao.getAll();
    }
    public void displayAllFlights() {
        IndexedDisplayer.displayIndexed(getAllFlights());
    }
}
