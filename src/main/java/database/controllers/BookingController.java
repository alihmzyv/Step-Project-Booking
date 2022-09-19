package database.controllers;

import entities.Booking;
import database.services.BookingService;
import database.services.FlightService;
import database.services.PassengerService;
import database.services.UserService;
import entities.Flight;
import entities.Passenger;

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
        bs.saveBooking(booking);
        us.addBooking(booking.getUser(), booking);
        fs.decrementCapacity(booking.getFlight());
        fs.addPassenger(booking.getFlight(), booking.getPassenger());
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

    public void setAllBookingsTo(List<Booking> data) {
        bs.setAllBookingsTo(data);
    }

    public boolean removeBooking(Booking booking){
        if (getBooking(booking).isEmpty()) {
            return false;
        }
        bs.removeBooking(booking);
        us.removeBooking(booking.getUser(), booking);
        fs.incrementCapacity(booking.getFlight());
        fs.removePassenger(booking.getFlight(), booking.getPassenger());
        return true;
    }


    public int getMaxId() {
        return bs.getMaxId();
    }
}
