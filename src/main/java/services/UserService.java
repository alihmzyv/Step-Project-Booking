package services;

import dao.DAO;
import entities.User;

import java.util.List;

public class UserService {
    private DAO<User> dao;

    public UserService(DAO<User> dao) {
        this.dao = dao;
    }

    public UserService() {
    }
    public List<User> getAllUsers() {
        return dao.getAll();
    }

    public void updateAll() {
        throw new RuntimeException("not imple");
    }
}
