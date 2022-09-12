package database.controllers;

import entities.Booking;
import database.services.BookingService;
import database.services.FlightService;
import database.services.PassengerService;
import database.services.UserService;

import java.util.List;
import java.util.Optional;

public class BookingController {
    private final BookingService bs;
    private final UserService us;
    private final FlightService fs;
    private final PassengerService ps;

    public BookingController(BookingService bs, UserService us, FlightService fs, PassengerService ps) {
        this.bs = bs;
        this.us = us;
        this.fs = fs;
        this.ps = ps;
    }

    public void saveBooking(Booking booking) {
        us.getUser(booking.getUser()).get().addBooking(booking);
        fs.getFlight(booking.getFlight()).get().decrementCapacity();
        ps.savePassenger(booking.getPassenger());
        bs.saveBooking(booking);
    }

    public Optional<Booking> getBooking(int id) {
        return bs.getBooking(id);
    }

    public Optional<Booking> getBooking(Booking booking) {
        return bs.getBooking(booking);
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

    public boolean removeBooking(int id){
        Optional<Booking> bookingFoundOptional = getBooking(id);
        if (bookingFoundOptional.isEmpty()) {
            return false;
        }
        Booking bookingFound = bookingFoundOptional.get();
        fs.getFlight(bookingFound.getFlight()).get().incrementCapacity();
        ps.removePassenger(bookingFound.getPassenger());
        us.getUser(bookingFound.getUser()).get().removeBooking(bookingFound);
        bs.removeBooking(id);
        return true;
    }

    public boolean removeBooking(Booking booking){
        Optional<Booking> bookingFoundOptional = getBooking(booking);
        if (bookingFoundOptional.isEmpty()) {
            return false;
        }
        Booking bookingFound = bookingFoundOptional.get();
        fs.getFlight(bookingFound.getFlight()).get().incrementCapacity();
        us.getUser(bookingFound.getUser()).get().removeBooking(booking);
        ps.removePassenger(bookingFound.getPassenger());
        bs.removeBooking(booking);
        return true;
    }

    public int getMaxId() {
        return bs.getMaxId();
    }
}
