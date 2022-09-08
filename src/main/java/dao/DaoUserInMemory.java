package dao;

import entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoUserInMemory implements DaoInMemory<User> {
    final private List<User> users = new ArrayList<>();

    @Override
    public void save(User object) {
        users.add(object);
    }

    @Override
    public Optional<User> get(int id) {
        return users.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();
    }

    @Override
    public void saveAll(List<User> objects) {
        users.addAll(objects);
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
