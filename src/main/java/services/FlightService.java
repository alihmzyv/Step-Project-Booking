package services;

import database.dao.DAO;
import entities.Flight;

import java.util.List;
import java.util.Optional;

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

    public Optional<Flight> getFlight(Flight flight) {
        return dao.get(flight);
    }

    public void saveAllFlights(List<Flight> flights) {
        dao.saveAll(flights);
    }
    public Optional<List<Flight>> getAllFlights() {
        return dao.getAll();
    }

    public void setAllFlights(List<Flight> flights) {
        dao.setAll(flights);
    }
    public boolean removeFlight(int id) {
        return dao.remove(id);
    }
    public boolean removeFlight(Flight flight) {
        return dao.remove(flight);
    }
    public void displayAllFlights() {
        getAllFlights().ifPresent(flights ->
                flights.forEach(flight -> System.out.println("\t\t\t" + flight)));
    }
    public boolean isPresent() {
        return dao.isPresent();
    }
    public boolean isEmpty() {
        return dao.isEmpty();
    }
    public int getMaxId() {
        return dao.getMaxId();
    }
}
