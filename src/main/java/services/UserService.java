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
    public void saveAllUsers(List<User> users) {
        dao.saveAll(users);
    }
    public List<User> getAllUsers() {
        return dao.getAll();
    }

}
