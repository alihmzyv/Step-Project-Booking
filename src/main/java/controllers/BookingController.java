package controllers;

import entities.Booking;
import services.BookingService;

import java.util.List;

public class BookingController {
    private BookingService fs;

    public BookingController(BookingService fs) {
        this.fs = fs;
    }

    public BookingController() {
        fs = new BookingService();
    }

    public List<Booking> getAllBookings() {
        return fs.getAllBookings();
    }

    public void updateAllBookings() {
        fs.updateAll();
    }
}
