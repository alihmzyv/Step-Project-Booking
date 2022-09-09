package database.dao;

import entities.Booking;

import java.util.List;

public class DaoBookingInMemory extends DaoInMemory<Booking> {
    public DaoBookingInMemory(List<Booking> bookings) {
        super(bookings);
    }
}
