package app;

import controllers.UserController;

public class Register extends MenuItem {
    private Database database;
    public Register (int id) {
        super(id);
    }


    @Override
    public void run() {
        throw new RuntimeException("not impl");
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
