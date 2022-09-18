package menu_and_menu_items;

import entities.User;

import java.util.List;

public class MyBookings extends MenuItem {
    private final User user;
    public MyBookings(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        getConsole().printAsIndexedTable(
                "YOUR BOOKINGS",
                List.of("PASSENGER", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF BOOKING"),
                user.getAllBookings(),
                100);
    }
}
