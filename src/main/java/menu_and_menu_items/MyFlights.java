package menu_and_menu_items;

import entities.User;

import java.util.List;

public class MyFlights extends MenuItem {
    private User user;
    public MyFlights(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        getConsole().printAsIndexedTable("YOUR FLIGHTS", List.of("PASSENGER", "FLIGHT", "FROM", "TO", "TIME OF BOOKING"),
                user.getAllBookings(),
                100);
    }
}
