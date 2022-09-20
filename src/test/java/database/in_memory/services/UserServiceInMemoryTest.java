package database.in_memory.services;

import database.dao.DaoUserInMemory;
import database.services.UserService;
import entities.Booking;
import entities.User;
import exceptions.booking_menu_exceptions.NoSuchUserException;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceInMemoryTest {
    List<User> randomUsers = User.getRandom(100);
    User randomUser = User.getRandom();
    Booking randomBooking = Booking.getRandom();

    @Test
    void getAllUsersTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertEquals(Optional.of(randomUsers), us.getAllUsers());
    }

    @Test
    void setAllUsersTo() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        List<User> randomUsers2 = User.getRandom(100);
        us.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), us.getAllUsers());
    }

    @Test
    void isPresentTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertTrue(us.isPresent());
    }

    @Test
    void isEmptyTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertFalse(us.isEmpty());
    }

    @Test
    void saveUserTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        us.saveUser(randomUser);
        assertTrue(us.isPresent());
        assertTrue(us.getAllUsers().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void getUserWithObjTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertEquals(Optional.empty(), us.getUser(randomUser));
    }

    @Test
    void getUserWithObjTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertEquals(Optional.empty(), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void removeUserWithObjTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertFalse(us.removeUser(randomUser));
    }

    @Test
    void removeUserWithObjTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        us.saveUser(randomUser);
        assertTrue(us.removeUser(randomUser));
    }

    @Test
    void addBookingTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertThrowsExactly(NoSuchUserException.class, () -> us.addBooking(randomUser, randomBooking));
    }

    @Test
    void addBookingTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        User randomUser = User.getRandom();
        us.saveUser(randomUser);
        assertTrue(us.addBooking(randomUser, randomBooking));
    }

    @Test
    void removeBookingTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertThrowsExactly(NoSuchUserException.class, () -> us.removeBooking(randomUser, randomBooking));
    }

    @Test
    void removeBookingTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        User randomUser = User.getRandom();
        randomUser.addBooking(randomBooking);
        us.saveUser(randomUser);
        assertTrue(us.removeBooking(randomUser, randomBooking));
    }

    @Test
    void containsTest1() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertFalse(us.contains(randomUser.getUsername()));
    }

    @Test
    void containsTest2() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        us.saveUser(randomUser);
        assertTrue(us.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxId() {
        UserService us = new UserService(new DaoUserInMemory(randomUsers));
        assertTrue(us.isPresent());
        assertEquals(us.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), us.getMaxId());
    }
}
