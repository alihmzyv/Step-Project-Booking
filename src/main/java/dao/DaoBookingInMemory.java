package dao;

import entities.Booking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoBookingInMemory implements DaoInMemory<Booking> {

    final private List<Booking> bookings = new ArrayList<>();

    @Override
    public void save(Booking object) {
        bookings.add(object);
    }

    @Override
    public Optional<Booking> get(int id) {
        return bookings.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    @Override
    public void saveAll(List<Booking> objects) {
        bookings.addAll(objects);
    }

    @Override
    public List<Booking> getAll() {
       return bookings;
    }
}
