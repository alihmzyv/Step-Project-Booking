package database.in_memory.services;

import database.dao.DaoUserInMemory;
import database.services.UserService;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceInMemoryTest {
    List<User> users = User.getRandom(100);
    User user = User.getRandom();

    @Test
    void saveUserTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertEquals(user, us.getUser(user).get());
    }

    @Test
    void getUserWithIdTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertTrue(us.getUser(user.getId()).isEmpty());
    }

    @Test
    void getUserWithIdTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertEquals(Optional.of(user), us.getUser(user.getId()));
    }

    @Test
    void getUserWithObjTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertTrue(us.getUser(user).isEmpty());
    }

    @Test
    void getUserWithObjTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertEquals(user, us.getUser(user).get());
    }

    @Test
    void getUserWithUsernameAndPsswrdTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertEquals(Optional.of(user), us.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertEquals(Optional.empty(), us.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getAllUsersTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertEquals(users, us.getAllUsers().get());
    }

    @Test
    void setAllUsersTo() {
        UserService us = new UserService(new DaoUserInMemory(users));
        List<User> users2 = User.getRandom(100);
        us.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), us.getAllUsers());
    }

    @Test
    void removeUserWithIdTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertFalse(us.removeUser(user.getId()));
    }

    @Test
    void removeUserWithIdTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertTrue(us.removeUser(user.getId()));
    }

    @Test
    void removeUserWithObjTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertFalse(us.removeUser(user));
    }

    @Test
    void removeUserWithObjTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertTrue(us.removeUser(user));
    }

    @Test
    void containsTest1() {
        UserService us = new UserService(new DaoUserInMemory(users));
        us.saveUser(user);
        assertTrue(us.contains(user.getUsername()));
    }

    @Test
    void containsTest2() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertFalse(us.contains(user.getUsername()));
    }

    @Test
    void getMaxId() {
        UserService us = new UserService(new DaoUserInMemory(users));
        assertEquals(us.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), us.getMaxId());
    }
}
