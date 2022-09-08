package dao;

import entities.Flight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoFlightInMemory implements DaoInMemory<Flight> {

    final private List<Flight> flights = new ArrayList<>();

    @Override
    public void save(Flight object) {
        flights.add(object);
    }

    @Override
    public Optional<Flight> get(int id) {
        return flights.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    @Override
    public void saveAll(List<Flight> objects) {
        flights.addAll(objects);
    }

    @Override
    public List<Flight> getAll() {
        return flights;
    }
}
