package menu_and_menu_items;

public class DisplayAllFlights extends MenuItem {
    public DisplayAllFlights(int id) {
        super(id);
    }
    public void run() {
        getDatabase().getFcInMemory().displayAllFlights(getConsole());
    }
}
