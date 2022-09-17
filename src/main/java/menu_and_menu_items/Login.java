package menu_and_menu_items;

import entities.User;
import exceptions.MenuException;
import exceptions.booking_menu_exceptions.BookingMenuException;
import exceptions.booking_menu_exceptions.NoSuchBookingException;
import exceptions.booking_menu_exceptions.NoSuchUserException;
import java.io.File;
import java.util.Optional;

public class Login extends MenuItem {

    public Login(int id) {
        super(id);
    }


    public void run() throws BookingMenuException {
        String username = getConsole().getInput("Enter your username:");
        String password = getConsole().getInput("Enter your password:");
        Optional<User> userOptional = getDatabase().getUcInMemory().getUser(username, password);
        if (userOptional.isEmpty()) {
            throw new NoSuchUserException("Wrong username and/or password. Try again.");
        }
        getLogger().loginInfo(userOptional.get());
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        System.out.println("Logging in...");
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userOptional.get(), getLogger()).run();
    }
}
