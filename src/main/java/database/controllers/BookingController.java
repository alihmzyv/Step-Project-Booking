package database.controllers;

import entities.Booking;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import exceptions.database_exceptions.NoSuchFlightException;
import exceptions.database_exceptions.NoSuchUserException;
import exceptions.database_exceptions.NonInstantiatedDaoException;

import java.util.List;
import java.util.Optional;

public class BookingController {
    private final BookingService bs;
    private final UserService us;
    private final FlightService fs;


    //constructors
    public BookingController(BookingService bs, UserService us, FlightService fs) {
        this.bs = bs;
        this.us = us;
        this.fs = fs;
    }


    /*
     * Saves the given booking to the booking database and to the bookings list of the given user in database,
     * decrease the capacity of the given flight by 1, add the given passenger to the passenger list of the flight in
     * the database.
     * if any of the user or flight databases are not instantiated (getAll() of dao returns empty Optional) or
     * any of the user or flight is not contained in the respective database, instantiates or adds respectively,
     * then carries out the procedure.
     */
    public void saveBooking(Booking booking) {
        bs.saveBooking(booking);
        try {
            us.addBooking(booking.getUser(), booking);
        }
        catch (NonInstantiatedDaoException | NoSuchUserException exc) {
            us.saveUser(booking.getUser());
            us.addBooking(booking.getUser(), booking);
        }
        try {
            fs.decrementCapacity(booking.getFlight());
            fs.addPassenger(booking.getFlight(), booking.getPassenger());
        }
        catch (NonInstantiatedDaoException | NoSuchFlightException exc) {
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
        bs.setAllBookings(data);
    }

    public boolean removeBooking(Booking booking){
        if (getBooking(booking).isEmpty()) {
            return false;
        }
        fs.incrementCapacity(booking.getFlight());
        return bs.removeBooking(booking) &&
                us.removeBooking(booking.getUser(), booking) &&
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
