package database.controllers;

import entities.User;
import database.services.UserService;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    public UserService getService() {
        return us;
    }

    public void saveUser(User user) {
        us.saveUser(user);
    }
    public Optional<User> getUser(int id) {
        return us.getUser(id);
    }

    public Optional<User> getUser(String username, String password) {
        return us.getUser(username, password);
    }

    public Optional<List<User>> getAllUsers() {
        return us.getAllUsers();
    }

    public void setAllUsersTo(List<User> data) {
        us.setAllUsersTo(data);
    }
    public boolean removeUser(int id) {
        return us.removeUser(id);
    }

    public boolean removeUser(User user) {
        return us.removeUser(user);
    }

    public int getMaxId() {
        return us.getMaxId();
    }

    public boolean contains(String username) {
        return us.contains(username);
    }
}
