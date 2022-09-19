package database.in_memory.dao;

import database.dao.DaoUserInMemory;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoUserInMemoryTest {
    private final List<User> randomUsers = User.getRandom(100);
    private final User randomUser = User.getRandom();

    @Test
    void saveTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertEquals(randomUser, daoUserInMemory.get(randomUser).get());
    }


    @Test
    void getWithIdTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertTrue(daoUserInMemory.get(randomUser.getId()).isEmpty());
    }

    @Test
    void getWithIdTest2() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserInMemory.get(randomUser.getId()));
    }

    @Test
    void getWithObjTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertTrue(daoUserInMemory.get(randomUser).isEmpty());
    }

    @Test
    void getWithObjTest2() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertEquals(randomUser, daoUserInMemory.get(randomUser).get());
    }

    @Test
    void removeWithIdTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertFalse(daoUserInMemory.remove(randomUser.getId()));
    }

    @Test
    void removeWithIdTest2() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertTrue(daoUserInMemory.remove(randomUser.getId()));
    }

    @Test
    void removeWithObjTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertFalse(daoUserInMemory.remove(randomUser));
    }

    @Test
    void removeWithObjTest2() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertTrue(daoUserInMemory.remove(randomUser));
    }

    @Test
    void getAllTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertEquals(randomUsers, daoUserInMemory.getAll().get());
    }

    @Test
    void saveAllTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        List<User> randomUsers2 = User.getRandom(100);
        daoUserInMemory.saveAll(randomUsers2);
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(randomUsers);
        allUsers.addAll(randomUsers2);
        assertEquals(Optional.of(allUsers), daoUserInMemory.getAll());
    }

    @Test
    void setAllTo() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        List<User> randomUsers2 = User.getRandom(100);
        daoUserInMemory.setAllTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), daoUserInMemory.getAll());
    }

    @Test
    void getMaxId() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertEquals(daoUserInMemory.getAll().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), daoUserInMemory.getMaxId());
    }
}