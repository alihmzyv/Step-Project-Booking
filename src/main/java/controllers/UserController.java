package controllers;

import entities.User;
import services.UserService;

import java.util.List;

public class UserController {
    private UserService fs;

    public UserController(UserService fs) {
        this.fs = fs;
    }

    public UserController() {
        fs = new UserService();
    }

    public List<User> getAllUsers() {
        return fs.getAllUsers();
    }

    public void updateAllUsers() {
        fs.updateAll();
    }
}
