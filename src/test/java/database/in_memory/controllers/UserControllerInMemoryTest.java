package database.in_memory.controllers;

import database.dao.DaoUserInMemory;
import database.services.UserService;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerInMemoryTest {
    List<User> users = User.getRandom(100);
    User user = User.getRandom();

    @Test
    void saveUserTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertEquals(user, fs.getUser(user).get());
    }

    @Test
    void getUserWithIdTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertTrue(fs.getUser(user.getId()).isEmpty());
    }

    @Test
    void getUserWithIdTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertEquals(Optional.of(user), fs.getUser(user.getId()));
    }

    @Test
    void getUserWithObjTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertTrue(fs.getUser(user).isEmpty());
    }

    @Test
    void getUserWithObjTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertEquals(user, fs.getUser(user).get());
    }

    @Test
    void getUserWithUsernameAndPsswrdTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertEquals(Optional.of(user), fs.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertEquals(Optional.empty(), fs.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getAllUsersTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertEquals(users, fs.getAllUsers().get());
    }

    @Test
    void setAllUsersTo() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        List<User> users2 = User.getRandom(100);
        fs.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), fs.getAllUsers());
    }

    @Test
    void removeUserWithIdTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertFalse(fs.removeUser(user.getId()));
    }

    @Test
    void removeUserWithIdTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertTrue(fs.removeUser(user.getId()));
    }

    @Test
    void removeUserWithObjTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertFalse(fs.removeUser(user));
    }

    @Test
    void removeUserWithObjTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertTrue(fs.removeUser(user));
    }

    @Test
    void containsTest1() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        fs.saveUser(user);
        assertTrue(fs.contains(user.getUsername()));
    }

    @Test
    void containsTest2() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertFalse(fs.contains(user.getUsername()));
    }

    @Test
    void getMaxId() {
        UserService fs = new UserService(new DaoUserInMemory(users));
        assertEquals(fs.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }
}
