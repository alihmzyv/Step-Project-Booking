package menu_items;

import entities.User;
import exceptions.NoSuchBookingException;

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
    public void run() {
        int bookingIndexInput;
        while (true) {
            bookingIndexInput = getConsole().getPositiveInt("Please enter the index of a booking you have:");
            try {
                if (getDatabase().getBcInMemory().removeBooking(user.getAllBookings().get(bookingIndexInput - 1))) {
                    System.out.println("Booking was cancelled!");
                    break;
                }
                throw new NoSuchBookingException("There is no such booking.");
            }
            catch (NoSuchBookingException exc) {
                getConsole().println(exc.getMessage());
                System.out.println("Try again.");
            }
        }
    }
}
