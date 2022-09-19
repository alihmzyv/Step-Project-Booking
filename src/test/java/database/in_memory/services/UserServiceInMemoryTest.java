package database.in_memory.services;

import database.controllers.UserController;
import database.dao.DaoUserInMemory;
import database.services.UserService;
import entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceInMemoryTest {

    List<User> users = User.getRandom(100);
    User user = User.getRandom();

    @Test
    void saveUserTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertEquals(user, uc.getUser(user).get());
    }

    @Test
    void getUserWithIdTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertTrue(uc.getUser(user.getId()).isEmpty());
    }

    @Test
    void getUserWithIdTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertEquals(Optional.of(user), uc.getUser(user.getId()));
    }

    @Test
    void getUserWithObjTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertTrue(uc.getUser(user).isEmpty());
    }

    @Test
    void getUserWithObjTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertEquals(user, uc.getUser(user).get());
    }

    @Test
    void getUserWithUsernameAndPsswrdTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertEquals(Optional.of(user), uc.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertEquals(Optional.empty(), uc.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getAllUsersTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertEquals(users, uc.getAllUsers().get());
    }

    @Test
    void setAllUsersTo() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        List<User> users2 = User.getRandom(100);
        uc.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), uc.getAllUsers());
    }

    @Test
    void removeUserWithIdTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertFalse(uc.removeUser(user.getId()));
    }

    @Test
    void removeUserWithIdTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertTrue(uc.removeUser(user.getId()));
    }

    @Test
    void removeUserWithObjTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertFalse(uc.removeUser(user));
    }

    @Test
    void removeUserWithObjTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertTrue(uc.removeUser(user));
    }

    @Test
    void containsTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        uc.saveUser(user);
        assertTrue(uc.contains(user.getUsername()));
    }

    @Test
    void containsTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertFalse(uc.contains(user.getUsername()));
    }

    @Test
    void getMaxId() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(users)));
        assertEquals(uc.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), uc.getMaxId());
    }
}