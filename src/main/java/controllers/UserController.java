package controllers;

import entities.User;
import services.UserService;

import java.util.List;
import java.util.Optional;

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

    public Optional<List<User>> getAllUsers() {
        return us.getAllUsers();
    }

    public void setAllUsers(List<User> data) {
        us.setAllUsers(data);
    }
}
