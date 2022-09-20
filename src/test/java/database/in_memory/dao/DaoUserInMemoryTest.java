package database.in_memory.dao;

import database.dao.DaoUserInMemory;
import database.dao.DaoUserInMemory;
import database.dao.DaoUserInMemory;
import entities.User;
import entities.User;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoUserInMemoryTest {
    private final List<User> randomUsers = User.getRandom(100);
    private final User randomUser = User.getRandom();

    @Test
    void getAllTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertEquals(Optional.of(randomUsers), daoUserInMemory.getAll());
    }

    @Test
    void saveAllTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        List<User> randomUsers2 = User.getRandom(100);
        daoUserInMemory.saveAll(randomUsers2);
        List<User> allUsers = new ArrayList<>(randomUsers);
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
    void isPresentTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertTrue(daoUserInMemory.isPresent());
    }

    @Test
    void isEmptyTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertFalse(daoUserInMemory.isEmpty());
    }

    @Test
    void saveTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertTrue(daoUserInMemory.isPresent());
        assertTrue(daoUserInMemory.getAll().get().stream()
                .anyMatch(flight -> flight.equals(randomUser)));
    }

    @Test
    void getWithIdTest1() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertEquals(Optional.empty(), daoUserInMemory.get(randomUser.getId()));
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
        assertEquals(Optional.empty(), daoUserInMemory.get(randomUser));
    }

    @Test
    void getWithObjTest2() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        daoUserInMemory.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserInMemory.get(randomUser));
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
    void getMaxId() {
        DaoUserInMemory daoUserInMemory = new DaoUserInMemory(randomUsers);
        assertTrue(daoUserInMemory.isPresent());
        assertEquals(daoUserInMemory.getAll().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), daoUserInMemory.getMaxId());
    }
}