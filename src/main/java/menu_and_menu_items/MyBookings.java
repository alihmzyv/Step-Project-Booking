package menu_and_menu_items;

import entities.User;

import java.util.List;

public class MyBookings extends MenuItem {
    private final User user;


    //constructors
    public MyBookings(int id, User user) {
        super(id);
        this.user = user;
    }


    //methods
    @Override
    public void run() {
        getConsole().printAsIndexedTable(
                "YOUR BOOKINGS",
                List.of("PASSENGER", "FLIGHT", "FROM", "TO", "DATE-TIME OF DEPARTURE", "DATE-TIME OF BOOKING"),
                user.getAllBookings(),
                100);
    }
}
