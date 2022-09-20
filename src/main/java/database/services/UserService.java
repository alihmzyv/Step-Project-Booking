package database.services;

import database.dao.DAO;
import entities.Booking;
import entities.User;
import exceptions.database_exceptions.NoSuchUserException;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final DAO<User> dao;


    //constructors
    public UserService(DAO<User> dao) {
        this.dao = dao;
    }


    //methods
    public void saveUser(User user) {
        dao.save(user);
    }

    public Optional<User> getUser(User user) {
        return dao.get(user);
    }

    public boolean removeUser(User user) {
        return dao.remove(user);
    }

    public Optional<List<User>> getAllUsers() {
        return dao.getAll();
    }
    
    public void setAllUsers(List<User> data) {
        dao.setAll(data);
    }

    public Optional<User> getUser(String username, String password) {
        requiresNonNull();
        return getAllUsers().get().stream()
                .filter(user ->
                        user.getUsername().equals(username) &&
                        user.getPassword().equals(password))
                .findAny();
    }

    /*
     * Returns true if the user with the given username is contained in the database, otherwise false.
     * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public boolean contains(String username) {
        requiresNonNull();
        return getAllUsers().get().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    /*
     * Adds the given booking to the bookings list of given user.
     * * which - user to which the booking should be added
     * * booking - booking to be added
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public void addBooking(User which, Booking booking) {
        requiresNonNull();
        List<User> users = getAllUsers().get();
        users.stream()
                .filter(user -> user.equals(which))
                .findAny()
                .orElseThrow(() ->
                        new NoSuchUserException(String.format("There is no such user in database: %s", which)))
                .addBooking(booking);
        setAllUsers(users);
    }

    /*
     * If the given user's bookings list contains the given booking, removes the booking from the list, returns true,
     * otherwise returns false.
     * * which - user from which the booking to be removed
     * * booking - booking to be removed
     * * throws NonInstantiatedDaoException if calling getAll() on dao would return empty Optional
     */
    public boolean removeBooking(User which, Booking booking) {
        requiresNonNull();
        List<User> users = getAllUsers().get();
        if (users.stream()
                .filter(user -> user.equals(which))
                .findAny()
                .orElseThrow(() -> new NoSuchUserException(String.format("There is no such user in database: %s", which)))
                .removeBooking(booking)) {
            setAllUsers(users);
            return true;
        }
        return false;
    }

    public boolean isPresent() {
        return dao.isPresent();
    }

    public boolean isEmpty() {
        return dao.isEmpty();
    }

    public void requiresNonNull() {
        dao.requiresNonNull();
    }

    public int getMaxId() {
        return dao.getMaxId();
    }
}
