package database.services;

import database.dao.DAO;
import entities.Passenger;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;

import java.util.List;
import java.util.Optional;

public class PassengerService {
    private final DAO<Passenger> dao;

    public PassengerService(DAO<Passenger> dao) {
        this.dao = dao;
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

    public boolean removePassenger(int id) {
        return dao.remove(id);
    }
    public boolean removePassenger(Passenger passenger) {
        return dao.remove(passenger);
    }

    public void saveAllPassengers(List<Passenger> passengers) {
        dao.saveAll(passengers);
    }
    public Optional<List<Passenger>> getAllPassengers() {
        return dao.getAll();
    }

    public void setAllPassengersTo(List<Passenger> data) {
        dao.setAllTo(data);
    }
    public boolean isEmpty() {
        return dao.isEmpty();
    }

    public boolean isPresent() {
        return dao.isPresent();
    }

    public int getMaxId() {
        return dao.getMaxId();
    }

    public boolean contains(Passenger passenger) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        return getAllPassengers().get().contains(passenger);
    }
}
