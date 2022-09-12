package menu_and_menu_items;

import entities.User;
import exceptions.MenuException;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class Register extends MenuItem {
    private User userRegistered;
    public Register (int id) {
        super(id);
    }


    @Override
    public void run() throws MenuException {
        String name = getConsole().getInput("Enter your name:");
        String surname = getConsole().getInput("Enter your surname:");
        String username = getConsole().getInput("Enter your username:");
        String password;
        while (true) {
            password = getConsole().getInput("Enter your password:");
            if (!Pattern.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", password)){
                System.out.println("""
                        Password should contain 6 characters and at least:
                        one upper-case and one lower-case English letter
                        one digit
                        one special character
                        no space""");
                System.out.println("Try again");
                continue;
            }
            break;
        }
        userRegistered = new User(name, surname, username, password);
        getDatabase().getUcInMemory().saveUser(userRegistered);
        System.out.println("Registration was successful!");
        System.out.println("Logging in...");
        getUserMenu(userRegistered).run();
    }

    private Menu getUserMenu(User user) {
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        List<MenuItem> userMenuItems = List.of(new DisplayAllFlights(1),
                new DisplayFlight(2),
                new FindAndBookFlight(3, new FindFlight(1), new BookFlight(2, user)),
                new CancelFlight(4, user),
                new MyFlights(5, user),
                new EndSession(6));
        return new Menu(getDatabase(), userMenuTextFile, userMenuItems);
    }
}
