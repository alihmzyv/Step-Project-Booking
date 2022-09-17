package menu_and_menu_items;

import entities.Booking;
import entities.User;
import exceptions.booking_menu_exceptions.NoSuchBookingException;

public class CancelBooking extends MenuItem {
    private final User user;

    public CancelBooking(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        Booking bookingInput = getBookingInput();
        if (removeBooking(bookingInput)) {
            logCancelling(bookingInput);
            getConsole().println("Booking was cancelled !");
        }
        else {
            throw new NoSuchBookingException("No such booking found in the database.");
        }
    }

    private Booking getBookingInput() {
        int bookingIndexInput = getConsole().getPositiveInt("Please enter the index of a booking you have:");
        try {
            return user.getAllBookings().get(bookingIndexInput - 1);
        }
        catch (IndexOutOfBoundsException exc) {
            throw new NoSuchBookingException(
                    String.format("There is no such booking index in your bookings list: %d", bookingIndexInput));
        }
    }

    private boolean removeBooking(Booking bookingInput) {
        return getDatabase().getBcInMemory().removeBooking(bookingInput);
    }

    private void logCancelling(Booking booking) {
        getLogger().cancelBookingInfo(user, booking);
    }
}
