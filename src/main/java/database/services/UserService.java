package database.services;

import database.dao.DAO;
import entities.Booking;
import entities.User;
import exceptions.booking_menu_exceptions.NoSuchUserException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;

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

    public Optional<User> getUser(User user) {
        return dao.get(user);
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

    public boolean addBooking(User which, Booking booking) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<User> users = getAllUsers().get();
        if (!users.contains(which)) {
            throw new NoSuchUserException(String.format("There is no such user in database: %s", which));
        }
        users.stream()
                .filter(user -> user.equals(which))
                .findAny()
                .get()
                .addBooking(booking);
        setAllUsersTo(users);
        return true;
    }

    public boolean removeBooking(User which, Booking booking) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        List<User> users = getAllUsers().get();
        if (!users.contains(which)) {
            throw new NoSuchUserException(String.format("There is no such user in database: %s", which));
        }
        if (users.stream()
                .filter(user -> user.equals(which))
                .findAny()
                .get()
                .removeBooking(booking)) {
            setAllUsersTo(users);
            return true;
        }
        else {
            return false;
        }
    }



    public boolean removeUser(int id) {
        return dao.remove(id);
    }
    public boolean removeUser(User user) {
        return dao.remove(user);
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
            throw new NonInitializedDatabaseException("""
                    Database Not Initialized.
                    (List field of DAO is null
                    or File field of DAO is an empty file or a file not containing a List of corresponding entity.""");
        }
        return getAllUsers().get().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
