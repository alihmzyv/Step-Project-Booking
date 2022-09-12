package database.dao;

import entities.User;

import java.util.List;

public class DaoUserInMemory extends DaoInMemory<User> {
    public DaoUserInMemory(List<User> users) {
        super(users);
    }

}
