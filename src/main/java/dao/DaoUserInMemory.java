package dao;

import entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoUserInMemory extends DaoInMemory<User> {

    public DaoUserInMemory(List<User> users) {
        super(users);
    }

}
