package database.services;

import database.dao.DAO;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final DAO<User> dao;

    public UserService(DAO<User> dao) {
        this.dao = dao;
    }

    public void saveUser(User user) {
        dao.save(user);
    }
    public Optional<User> getUser(int id) {
        return dao.get(id);
    }
    public Optional<User> getUser(String username, String password) {
        if (isEmpty()) {
            return Optional.empty();
        }
        return dao.getAll().get().stream()
                .filter(user ->
                        user.getUsername().equals(username) &&
                        user.getPassword().equals(password))
                .findAny();
    }

    public boolean removeUser(int id) {
        return dao.remove(id);
    }
    public boolean removeUser(User user) {
        return dao.remove(user);
    }

    public Optional<User> getUser(User user) {
        return dao.get(user);
    }

    public void setAllUsersTo(List<User> data) {
        dao.setAllTo(data);
    }

    public Optional<List<User>> getAllUsers() {
        return dao.getAll();
    }

    public boolean isEmpty() {
        return dao.isEmpty();
    }

    public boolean isPresent() {
        return dao.isPresent();
    }

    public int getMaxId() {
        return dao.getMaxId();
    }
    public boolean contains(String username) {
        if (isEmpty()) {
            return false;
        }
        return dao.getAll().get().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
