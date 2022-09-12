package menu_and_menu_items;

import database.Database;
import entities.User;
import exceptions.*;
import exceptions.booking_menu_exceptions.WrongMenuItemMenuException;
import io.Console;
import io.RealConsole;

import java.io.*;
import java.util.List;

public class Menu {
    private final List<MenuItem> menuItems;
    private final File menuTextFile;
    private String menuText;
    private final Database database;
    private final Console console;
    private User user;


    public Menu(Database database, File menuTextFile, List<MenuItem> menuItems) {
        this.database = database;
        this.menuTextFile = menuTextFile;
        try {
            menuText = getMenuText();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Could not find the main menu text file.");
            System.exit(1);
        }
        catch (IOException exc) {
            System.out.println("Could not read the main menu.");
            System.exit(1);
        }
        this.menuItems = menuItems;
        console = new RealConsole();
        menuItems
                .forEach(menuItem -> {
                    menuItem.setDatabase(this.database);
                    menuItem.setConsole(console);
                });
    }

    public Menu(Database database, File menuTextFile, List<MenuItem> menuItems, User user) {
        this.database = database;
        this.menuTextFile = menuTextFile;
        this.menuItems = menuItems;
        console = new RealConsole();
        menuItems
                .forEach(menuItem -> {
                    menuItem.setDatabase(this.database);
                    menuItem.setConsole(console);
                });
    }


    public void run() throws MenuException {
        while (true) {
            console.println(menuText);
            int menuItemIdInput = console.getPositiveInt("Please select an item from the menu displayed above:");
            MenuItem menuItemChosen = menuItems.stream()
                    .filter(menuItem -> menuItem.getId() == menuItemIdInput)
                    .findFirst()
                    .orElseThrow(() -> new WrongMenuItemMenuException("Wrong menu item. Try again."));
            menuItemChosen.run();
            if (menuItemChosen instanceof ExitButton) {
                break;
            }
        }
    }

    public String getMenuText() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(menuTextFile))) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .forEach(line -> sb.append(line).append("\n"));
            return sb.toString();
        }
    }
}
