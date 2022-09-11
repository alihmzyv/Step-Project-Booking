package menu_items;

import database.Database;
import io.Console;

import java.util.ArrayList;
import java.util.List;

public class DisplayAllFlights extends MenuItem {
    public DisplayAllFlights(int id) {
        super(id);
    }
    @Override
    public void run() {
        getConsole().printAsTable(List.of("Flight", "ID", "From", "To", "Time of Departure", "Time of Landing", "Flight Duration"),
                getDatabase().getFcInMemory().getAllFlights().orElseGet(ArrayList::new),
                110);
    }
}
