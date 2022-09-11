package services;

import database.dao.DAO;
import entities.Booking;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private DAO<Booking> dao;

    public BookingService(DAO<Booking> dao) {
        this.dao = dao;
    }

    public BookingService() {
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

    public void saveAllBookings(List<Booking> bookings) {
        dao.saveAll(bookings);
    }

    public Optional<List<Booking>> getAllBookings() {
        return dao.getAll();
    }

    public void setAllBookings(List<Booking> data) {
        dao.setAll(data);
    }

    public boolean removeBooking(int id) {
        return dao.remove(id);
    }

    public boolean removeBooking(Booking booking) {
        return dao.remove(booking);
    }

    public int getMaxId() {
        return dao.getMaxId();
    }
}
