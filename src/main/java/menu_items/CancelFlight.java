package menu_items;

import database.Database;
import entities.User;
import exceptions.NoSuchBookingException;
import io.Console;

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
        int bookingIdInput;
        while (true) {
            bookingIdInput = getConsole().getPositiveInt("Please enter the id of a booking you have.");
            if (user.cancelBooking(bookingIdInput) && getDatabase().getBcInMemory().deleteBooking(bookingIdInput)) {
                break;
            }
            getConsole().println("There is no booking found. Try again.");
        }
    }
}
