package menu_and_menu_items;

import java.time.Duration;

public class DisplaySomeFlights extends MenuItem {
    private final Duration withinNext;


    //constructors
    public DisplaySomeFlights(int id, Duration withinNext) {
        super(id);
        this.withinNext = withinNext;
    }


    //methods
    @Override
    public void run() {
        getDatabase().getFcInMemory().displayFlights(withinNext, getConsole());
    }
}
