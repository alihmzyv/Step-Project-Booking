package app;

import controllers.UserController;
import entities.User;

public class Login extends MenuItem {
    private Database database;

    public Login(int id) {
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
