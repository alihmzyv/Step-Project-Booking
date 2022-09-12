package menu_and_menu_items;

import database.Database;
import entities.User;

import java.util.ArrayList;
import java.util.List;

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
        getConsole().printAsIndexedTable(List.of("Passenger", "Flight", "From", "To", "Time of Booking"),
                user.getAllBookings(),
                100);
    }
}
