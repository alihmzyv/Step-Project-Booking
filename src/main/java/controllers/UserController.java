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
    public Optional<User> getUser(int id) {
        return us.getUser(id);
    }

    public Optional<User> getUser(User user) {
        return us.getUser(user);
    }

    public Optional<List<User>> getAllUsers() {
        return us.getAllUsers();
    }

    public void setAllUsers(List<User> data) {
        us.setAllUsers(data);
    }
    public boolean removeUser(int id) {
        Optional<User> userOptional = getUser(id);
        if (userOptional.isEmpty()) {
            return false;
        }
        return us.removeUser(userOptional.get());
    }

    public boolean removeUser(User user) {
        Optional<User> userOptional = getUser(user);
        if (userOptional.isEmpty()) {
            return false;
        }
        return us.removeUser(userOptional.get());
    }

    public int getMaxId() {
        return us.getMaxId();
    }
}
