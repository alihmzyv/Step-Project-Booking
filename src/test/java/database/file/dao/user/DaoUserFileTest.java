package database.file.dao.user;

import database.dao.DaoUserFile;
import entities.User;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoUserFileTest {
    List<User> randomUsers = User.getRandom(100);
    User randomUser = User.getRandom();
    private final File file = new File("src/test/java/database/file/files/users.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomUsers);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    @Test
    void getAllTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.of(randomUsers), daoUserFile.getAll());
    }

    @Test
    void getAllTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.empty(), daoUserFile.getAll());
    }

    @Test
    void getAllTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoUserFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.saveAll(users2);
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(randomUsers);
        allUsers.addAll(users2);
        assertEquals(Optional.of(allUsers), daoUserFile.getAll());
    }

    @Test
    void saveAllTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.saveAll(users2);
        assertEquals(Optional.of(users2), daoUserFile.getAll());
    }

    @Test
    void saveAllTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        List<User> users2 = User.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.saveAll(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.setAll(users2);
        assertEquals(Optional.of(users2), daoUserFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.setAll(users2);
        assertEquals(Optional.of(users2), daoUserFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        List<User> users2 = User.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.setAll(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertTrue(daoUserFile.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.isPresent());
    }

    @Test
    void isPresentTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertTrue(daoUserFile.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.isPresent());
        assertTrue(daoUserFile.getAll().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.isPresent());
        assertTrue(daoUserFile.getAll().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void saveTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.save(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.empty(), daoUserFile.get(randomUser.getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserFile.get(randomUser.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoUserFile.get(randomUser.getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.get(randomUser.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.empty(), daoUserFile.get(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserFile.get(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoUserFile.get(randomUser));
    }

    @Test
    void getWithObjTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.get(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.remove(randomUser));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.remove(randomUser.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoUserFile.remove(randomUser.getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.remove(randomUser.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.remove(randomUser));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.remove(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoUserFile.remove(randomUser));
    }

    @Test
    void removeWithObjTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoUserFile.remove(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertTrue(daoUserFile.isPresent());
        assertEquals(daoUserFile.getAll().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), daoUserFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertTrue(daoUserFile.isEmpty());
        assertEquals(1, daoUserFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoUserFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}