package menu_and_menu_items;

import java.time.Duration;

public class DisplayAllFlights extends MenuItem {
    public DisplayAllFlights(int id) {
        super(id);
    }
    public void run() {
        getDatabase().getFcInMemory().displayFlights(Duration.ofHours(24), getConsole());
    }
}
