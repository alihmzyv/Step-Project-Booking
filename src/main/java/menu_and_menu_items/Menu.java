package menu_and_menu_items;

import database.Database;
import entities.BookingLogger;
import exceptions.booking_menu_exceptions.BookingMenuException;
import exceptions.booking_menu_exceptions.WrongMenuItemMenuException;
import io.Console;

import java.io.*;
import java.util.List;

public class Menu {
    private final List<MenuItem> menuItems;
    private String menuText;
    private final Database database;
    private final Console console;


    public Menu(Database database, Console console, List<MenuItem> menuItems, File menuTextFile, BookingLogger logger) {
        this.database = database;
        this.console = console;
        try(BufferedReader br = new BufferedReader(new FileReader(menuTextFile))) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .forEach(line -> sb.append(line).append("\n"));
            menuText = sb.toString();
        }
        catch (FileNotFoundException exc) {
            System.out.printf("Could not find menu text file at:\n%s\n", menuTextFile.getAbsolutePath());
            System.exit(1);
        }
        catch (IOException exc) {
            System.out.printf("Could not read menu text file at:\n%s\n", menuTextFile.getAbsolutePath());
            System.exit(1);
        }
        this.menuItems = menuItems;
        menuItems
                .forEach(menuItem -> {
                    menuItem.setDatabase(database);
                    menuItem.setConsole(console);
                    menuItem.setLogger(logger);
                });
    }


    public void run() throws BookingMenuException {
        while (true) {
            console.println(menuText);
            int menuItemIdInput = console.getPositiveInt("Please select an item from the menu displayed above:");
            try {
                MenuItem menuItemChosen = menuItems.stream()
                        .filter(menuItem -> menuItem.getId() == menuItemIdInput)
                        .findFirst()
                        .orElseThrow(() -> new WrongMenuItemMenuException("Wrong menu item. Try again."));
                menuItemChosen.run();
                if (menuItemChosen instanceof ExitButton) {
                    break;
                }
            }
            catch (BookingMenuException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
