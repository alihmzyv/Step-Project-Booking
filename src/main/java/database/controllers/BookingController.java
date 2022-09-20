package database.controllers;

import entities.Booking;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import exceptions.database_exceptions.NoSuchFlightException;
import exceptions.database_exceptions.NoSuchUserException;

import java.util.List;
import java.util.Optional;

public class BookingController {
    private final BookingService bs;
    private final UserService us;
    private final FlightService fs;

    public BookingController(BookingService bs, UserService us, FlightService fs) {
        this.bs = bs;
        this.us = us;
        this.fs = fs;
    }

    public void saveBooking(Booking booking) {
        bs.saveBooking(booking);
        try {
            us.addBooking(booking.getUser(), booking);
        }
        catch (NoSuchUserException exc) {
            us.saveUser(booking.getUser());
            us.addBooking(booking.getUser(), booking);
        }
        try {
            fs.decrementCapacity(booking.getFlight());
            fs.addPassenger(booking.getFlight(), booking.getPassenger());
        }
        catch (NoSuchFlightException exc) {
            fs.saveFlight(booking.getFlight());
            fs.decrementCapacity(booking.getFlight());
            fs.addPassenger(booking.getFlight(), booking.getPassenger());
        }
    }

    public Optional<Booking> getBooking(Booking booking) {
        return bs.getBooking(booking);
    }

    public Optional<List<Booking>> getAllBookings() {
        return bs.getAllBookings();
    }

    public void setAllBookingsTo(List<Booking> data) {
        bs.setAllBookingsTo(data);
    }

    public boolean removeBooking(Booking booking){
        if (getBooking(booking).isEmpty()) {
            return false;
        }
        return bs.removeBooking(booking) &&
                us.removeBooking(booking.getUser(), booking) &&
                fs.incrementCapacity(booking.getFlight()) &&
                fs.removePassenger(booking.getFlight(), booking.getPassenger());
    }

    public boolean isPresent() {
        return bs.isPresent();
    }

    public boolean isEmpty() {
        return bs.isEmpty();
    }

    public int getMaxId() {
        return bs.getMaxId();
    }
}
