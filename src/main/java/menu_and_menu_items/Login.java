package menu_and_menu_items;

import entities.User;
import exceptions.MenuException;
import exceptions.booking_menu_exceptions.NoSuchUserException;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Login extends MenuItem {
    private User userLoggedIn;

    public Login(int id) {
        super(id);
    }


    public void run() throws MenuException {
        String username = getConsole().getInput("Enter your username:");
        String password = getConsole().getInput("Enter your password:");
        Optional<User> userOptional = getDatabase().getUcInMemory().getUser(username, password);
        if (userOptional.isEmpty()) {
            throw new NoSuchUserException("Wrong username and/or password. Try again.");
        }
        System.out.println("Logging in...");
        getUserMenu(userOptional.get()).run();
    }

    private Menu getUserMenu(User user) {
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        List<MenuItem> userMenuItems = List.of(new DisplayAllFlights(1),
                new DisplayFlight(2),
                new FindAndBookFlight(3, new FindFlight(1), new BookFlight(2, user)),
                new CancelFlight(4, user),
                new MyFlights(5, user),
                new EndSession(6));
        userMenuItems.forEach(menuItem -> {
            menuItem.setConsole(getConsole());
            menuItem.setDatabase(getDatabase());
        });
        return new Menu(getDatabase(), userMenuTextFile, userMenuItems, user);
    }

}
