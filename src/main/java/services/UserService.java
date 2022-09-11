package services;

import database.dao.DAO;
import entities.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private DAO<User> dao;

    public UserService(DAO<User> dao) {
        this.dao = dao;
    }

    public UserService() {
    }

    public void saveUser(User user) {
        dao.save(user);
    }
    public Optional<User> getUser(int id) {
        return dao.get(id);
    }
    public Optional<User> getUser(User user) {
        return dao.get(user);
    }
    public void saveAllUsers(List<User> users) {
        dao.saveAll(users);
    }

    public void setAllUsers(List<User> data) {
        dao.setAll(data);
    }

    public Optional<List<User>> getAllUsers() {
        return dao.getAll();
    }
    public boolean removeUser(int id) {
        return dao.remove(id);
    }
    public boolean removeUser(User user) {
        return dao.remove(user);
    }
    public int getMaxId() {
        return dao.getMaxId();
    }
}
