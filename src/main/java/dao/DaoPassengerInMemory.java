package dao;

import entities.Passenger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoPassengerInMemory implements DaoInMemory<Passenger> {
    final private List<Passenger> passenger = new ArrayList<>();

    @Override
    public void save(Passenger object) {
        passenger.add(object);
    }

    @Override
    public Optional<Passenger> get(int id) {
        return passenger.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    @Override
    public void saveAll(List<Passenger> objects) {
        passenger.addAll(objects);
    }

    @Override
    public List<Passenger> getAll() {
        return passenger;
    }
}
