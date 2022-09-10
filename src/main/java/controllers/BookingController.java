package controllers;

import entities.Booking;
import entities.Flight;
import exceptions.NoSuchBookingException;
import services.BookingService;
import services.FlightService;
import services.PassengerService;
import services.UserService;

import java.util.List;
import java.util.Optional;

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
        bs.saveBooking(booking);
        ps.savePassenger(booking.getPassenger());
        booking.getUser().addBooking(booking);
        booking.getFlight().decrementCapacity();
    }

    public Optional<Booking> getBooking(int id) {
        return bs.getBooking(id);
    }

    public void saveAllBookings(List<Booking> bookings) {
        bs.saveAllBookings(bookings);
    }

    public Optional<List<Booking>> getAllBookings() {
        return bs.getAllBookings();
    }

    public void setAllBookings(List<Booking> data) {
        bs.setAllBookings(data);
    }

    public boolean deleteBooking(int id){
        Optional<Booking> bookingFoundOptional = getBooking(id);
        if (bookingFoundOptional.isEmpty()) {
            return false;
        }
        Booking bookingFound = bookingFoundOptional.get();
        bookingFound.getFlight().incrementCapacity();
        ps.removePassenger(bookingFound.getPassenger().getId());
        bs.removeBooking(id);
        return true;
    }

    public int getMaxId() {
        return bs.getMaxId();
    }
}
