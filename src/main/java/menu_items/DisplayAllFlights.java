package menu_items;

import database.Database;
import io.Console;

public class DisplayAllFlights extends MenuItem {
    public DisplayAllFlights(int id) {
        super(id);
    }
    @Override
    public void run() {
        getDatabase().getFcInMemory().displayAllFlights();
    }
}
