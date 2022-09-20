package database.in_memory.controllers;

import database.controllers.UserController;
import database.dao.DaoUserInMemory;
import database.services.UserService;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerInMemoryTest {

    List<User> randomUsers = User.getRandom(100);
    User randomUser = User.getRandom();

    @Test
    void getAllUsersTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertEquals(randomUsers, uc.getAllUsers().get());
    }

    @Test
    void setAllUsersTo() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        List<User> randomUsers2 = User.getRandom(100);
        uc.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), uc.getAllUsers());
    }

    @Test
    void isPresentTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertTrue(uc.isPresent());
    }

    @Test
    void isEmptyTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertFalse(uc.isEmpty());
    }

    @Test
    void saveUserTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        uc.saveUser(randomUser);
        assertTrue(uc.isPresent());
        assertTrue(uc.getAllUsers().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void getUserWithObjTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertTrue(uc.getUser(randomUser).isEmpty());
    }

    @Test
    void getUserWithObjTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        uc.saveUser(randomUser);
        assertEquals(randomUser, uc.getUser(randomUser).get());
    }

    @Test
    void getUserWithUsernameAndPsswrdTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        uc.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getUserWithUsernameAndPsswrdTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertEquals(Optional.empty(), uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void removeUserWithObjTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertFalse(uc.removeUser(randomUser));
    }

    @Test
    void removeUserWithObjTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        uc.saveUser(randomUser);
        assertTrue(uc.removeUser(randomUser));
    }

    @Test
    void containsTest1() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        uc.saveUser(randomUser);
        assertTrue(uc.contains(randomUser.getUsername()));
    }

    @Test
    void containsTest2() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertFalse(uc.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxId() {
        UserController uc = new UserController(new UserService(new DaoUserInMemory(randomUsers)));
        assertEquals(uc.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), uc.getMaxId());
    }
}