package dao;

import entities.Booking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoBookingInMemory extends DaoInMemory<Booking> {
    public DaoBookingInMemory(List<Booking> bookings) {
        super(bookings);
    }
}
