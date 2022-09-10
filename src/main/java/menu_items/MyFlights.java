package menu_items;

import database.Database;
import entities.IndexedDisplayer;
import entities.User;
import io.Console;

public class MyFlights extends MenuItem {
    private User user;
    private Database database;
    public MyFlights(int id) {
        super(id);
    }

    public MyFlights(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("\t\t\t" + "=".repeat(100));
        System.out.println(String.join(" || ",
                "\t\t\tBooking ID", "Passenger", "Flight", "From", "To", "Time of Booking"));
        System.out.println("\t\t\t" + "-".repeat(100));
        user.getAllBookings().forEach(booking -> System.out.println("\t\t\t" + booking));
        System.out.println("\t\t\t" + "=".repeat(100));
    }
}
