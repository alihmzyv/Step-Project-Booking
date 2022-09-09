package controllers;

import entities.Booking;
import services.BookingService;
import services.FlightService;
import services.PassengerService;
import services.UserService;

import java.util.List;

public class BookingController {
    private BookingService bs;
    private UserService us;
    private FlightService fs;
    private PassengerService ps;

    public BookingController(BookingService bs, UserService us, FlightService fs, PassengerService ps) {
        this.bs = bs;
        this.us = us;
        this.fs = fs;
        this.ps = ps;
    }

    public BookingController() {
        bs = new BookingService();
        us = new UserService();
        fs = new FlightService();
        ps = new PassengerService();
    }

    public void saveBooking(Booking booking) {
//        bs.saveBooking(booking);
//        us.getUser(booking.getUser().getId()).get().addBooking(booking);
//        fs.getFlight(booking.getFlight().getId()).get().decrementCapacity();
//        ps.savePassenger(booking.getPassenger());
    }

    public List<Booking> getAllBookings() {
        return bs.getAllBookings();
    }
}
