package menu_and_menu_items;

import entities.Booking;
import entities.User;
import exceptions.booking_menu_exceptions.NoSuchBookingException;

public class CancelFlight extends MenuItem {
    private User user;

    public CancelFlight(int id) {
        super(id);
    }

    public CancelFlight(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() throws NoSuchBookingException {
        int bookingIndexInput;
        bookingIndexInput = getConsole().getPositiveInt("Please enter the index of a booking you have:");
        try {
            Booking booking = user.getAllBookings().get(bookingIndexInput - 1);
            if (getDatabase().getBcInMemory().removeBooking(booking)) {
                getLogger().cancelBookingInfo(user, booking);
                getConsole().println("Booking was cancelled!");
            }
            else {
                throw new NoSuchBookingException("No such booking found in the database.");
            }
        }
        catch (IndexOutOfBoundsException exc) {
            getConsole().println("There is no such booking index in your bookings list.");
        }
    }
}
