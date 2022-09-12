package menu_and_menu_items;

import exceptions.booking_menu_exceptions.NoSuchFlightException;

public class DisplayFlight extends MenuItem {
    public DisplayFlight(int id) {
        super(id);
    }


    public void run() throws NoSuchFlightException {
        getDatabase().getFcInMemory().displayFlight(
                getConsole().getPositiveInt("Please enter the id of the flight:"), getConsole());
    }
}
