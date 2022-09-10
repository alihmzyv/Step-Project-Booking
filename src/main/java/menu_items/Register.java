package menu_items;

import database.Database;
import entities.User;
import exceptions.NoSuchUserException;
import io.Console;

import java.io.File;
import java.util.List;

public class Register extends MenuItem {
    private User userRegistered;
    public Register (int id) {
        super(id);
    }



    @Override
    public void run() {
        String name = getConsole().getInput("Enter your name:");
        String surname = getConsole().getInput("Enter your surname:");
        String username = getConsole().getInput("Enter your username:");
        String password = getConsole().getInput("Enter your password:");
        userRegistered = new User(name, surname, username, password);
        getDatabase().getUcInMemory().saveUser(userRegistered);
        System.out.println("Registration was successful!");
        getUserMenu(userRegistered).run();
    }

    private Menu getUserMenu(User user) {
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        List<MenuItem> userMenuItems = List.of(new DisplayAllFlights(1),
                new DisplayFlight(2),
                new BookFlight(3, user),
                new CancelFlight(4, user),
                new MyFlights(5, user),
                new EndSession(6));
        return new Menu(getDatabase(), userMenuTextFile, userMenuItems);
    }
}
