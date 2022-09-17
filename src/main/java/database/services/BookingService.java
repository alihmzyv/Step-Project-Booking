package database.services;

import database.dao.DAO;
import entities.Booking;

import java.util.List;
import java.util.Optional;

public class BookingService {
    private final DAO<Booking> dao;

    public BookingService(DAO<Booking> dao) {
        this.dao = dao;
    }

    public void saveBooking(Booking booking) {
        dao.save(booking);
    }
    public Optional<Booking> getBooking(int id) {
        return dao.get(id);
    }

    public Optional<Booking> getBooking(Booking booking) {
        return dao.get(booking);
    }

    public boolean removeBooking(int id) {
        return dao.remove(id);
    }

    public boolean removeBooking(Booking booking) {
        return dao.remove(booking);
    }


    public void saveAllBookings(List<Booking> bookings) {
        dao.saveAll(bookings);
    }

    public Optional<List<Booking>> getAllBookings() {
        return dao.getAll();
    }

    public void setAllBookingsTo(List<Booking> data) {
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
}
