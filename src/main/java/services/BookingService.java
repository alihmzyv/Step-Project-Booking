package services;

import dao.DAO;
import entities.Booking;

import java.util.List;

public class BookingService {
    private DAO<Booking> dao;

    public BookingService(DAO<Booking> dao) {
        this.dao = dao;
    }

    public BookingService() {
    }
    public List<Booking> getAllBookings() {
        return dao.getAll();
    }

    public void updateAll() {
        throw new RuntimeException("not imple");
    }
}
