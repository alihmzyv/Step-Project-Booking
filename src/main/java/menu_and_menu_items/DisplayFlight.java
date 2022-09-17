package menu_and_menu_items;

import entities.User;
import exceptions.booking_menu_exceptions.NoSuchFlightException;

import java.util.Optional;

public class DisplayFlight extends MenuItem {
    private User user;
    public DisplayFlight(int id) {
        super(id);
    }

    public DisplayFlight(int id, User user) {
        super(id);
        this.user = user;
    }

    public void run() throws NoSuchFlightException {
        int flightIdInput = getConsole().getPositiveInt("Please enter the id of the flight:");
        getDatabase().getFcInMemory().displayFlight(flightIdInput, getConsole());
        if (getUser().isPresent()) {
            getLogger().searchInfo(user, flightIdInput);
        }
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}
