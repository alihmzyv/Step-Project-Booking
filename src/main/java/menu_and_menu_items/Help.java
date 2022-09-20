package menu_and_menu_items;

import helpers.Helper;

import java.io.Console;
import java.io.File;

public class Help extends MenuItem {
    private final String text;
    public Help(int id, File file) {
        super(id);
        text = Helper.readTextFile(file);
    }

    @Override
    public void run() {
        getConsole().println(text);
    }
}
