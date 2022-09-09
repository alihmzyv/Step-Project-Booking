package controllers;

import entities.User;
import services.UserService;

import java.util.List;

public class UserController {
    private UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    public UserController() {
        us = new UserService();
    }

    public UserService getService() {
        return us;
    }

    public void saveUser(User user) {
        us.saveUser(user);
    }

    public List<User> getAllUsers() {
        return us.getAllUsers();
    }
}
