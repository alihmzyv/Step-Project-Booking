package menu_and_menu_items;

import java.time.Duration;

public class DisplaySomeFlights extends MenuItem {
    private final Duration withinNext;
    public DisplaySomeFlights(int id, Duration withinNext) {
        super(id);
        this.withinNext = withinNext;
    }

    @Override
    public void run() {
        getDatabase().getFcInMemory().displayFlights(withinNext, getConsole());
    }
}
