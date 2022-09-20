package database.services;

import database.dao.DAO;
import entities.Booking;

import java.util.List;
import java.util.Optional;

public class BookingService {
    private final DAO<Booking> dao;


    //constructors
    public BookingService(DAO<Booking> dao) {
        this.dao = dao;
    }


    //methods
    public void saveBooking(Booking booking) {
        dao.save(booking);
    }

    public Optional<Booking> getBooking(Booking booking) {
        return dao.get(booking);
    }

    public boolean removeBooking(Booking booking) {
        return dao.remove(booking);
    }

    public Optional<List<Booking>> getAllBookings() {
        return dao.getAll();
    }

    public void setAllBookings(List<Booking> data) {
        dao.setAll(data);
    }

    public boolean isPresent() {
        return dao.isPresent();
    }

    public boolean isEmpty() {
        return dao.isEmpty();
    }

    public void requiresNonNull() {
        dao.requiresNonNull();
    }

    public int getMaxId() {
        return dao.getMaxId();
    }
}
