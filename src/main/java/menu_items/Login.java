package menu_items;

import database.Database;
import entities.User;
import exceptions.NoSuchUserException;
import io.Console;

import java.io.File;
import java.util.List;

public class Login extends MenuItem {
    private Database database;
    private User userLoggedIn;

    public Login(int id) {
        super(id);
    }


    @Override
    public void run() {
        while (true) {
            String username = getConsole().getInput("Enter your username:");
            String password = getConsole().getInput("Enter your password:");
            try {
                userLoggedIn = getDatabase().getUcInMemory().getAllUsers().stream()
                        .filter(user -> user.getUsername().equals(username) &&
                                user.getPassword().equals(password))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchUserException("Wrong username and/or password. Try again."));
                break;
            }
            catch (NoSuchUserException exc) {
                getConsole().println(exc.getMessage());
            }

            getUserMenu(userLoggedIn).run();
        }


    }

    private Menu getUserMenu(User user) {
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        List<MenuItem> userMenuItems = List.of(new DisplayAllFlights(1),
                new DisplayFlight(2),
                new BookFlight(3, user),
                new CancelFlight(4, user),
                new MyFlights(5, user),
                new EndSession(6));
        return new Menu(this.database, userMenuTextFile, userMenuItems, user);
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    protected void setConsole(Console console) {

    }
}
