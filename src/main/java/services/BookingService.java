package services;

import database.dao.DAO;
import entities.Booking;

import java.awt.print.Book;
import java.util.List;

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
    public void getBooking(int id) {
        dao.get(id);
    }

    public void saveAllBookings(List<Booking> bookings) {
        dao.saveAll(bookings);
    }

    public List<Booking> getAllBookings() {
        return dao.getAll();
    }
}
