package menu_items;

import database.Database;
import io.Console;

public class DisplayAllFlights extends MenuItem {
    public DisplayAllFlights(int id) {
        super(id);
    }
    @Override
    public void run() {
        System.out.println("\t\t\t" + "=".repeat(111));
        System.out.println(String.join(" || ",
                "\t\t\tFlight", "From", "To", "Time of Departure", "Time of Landing", "Flight Duration"));
        System.out.println("\t\t\t" + "-".repeat(111));
        getDatabase().getFcInMemory().displayAllFlights();
        System.out.println("\t\t\t" +"=".repeat(111));
    }
}
