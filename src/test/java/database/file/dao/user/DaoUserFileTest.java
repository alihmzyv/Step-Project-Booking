package database.file.dao.user;

import database.dao.DaoUserFile;
import entities.User;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoUserFileTest {
    List<User> users = User.getRandom(100);
    private final File file = new File("src/test/java/database/file/files/users.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    @Test
    void getAllTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.of(users), daoUserFile.getAll());
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoUserFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.setAllTo(users2);
        assertEquals(Optional.of(users2), daoUserFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.setAllTo(users2);
        assertEquals(Optional.of(users2), daoUserFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        List<User> users2 = User.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.setAllTo(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        List<User> usersCopy = new ArrayList<>(users);
        usersCopy.add(randomUser);
        assertEquals(Optional.of(usersCopy), daoUserFile.getAll());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        assertEquals(Optional.of(List.of(randomUser)), daoUserFile.getAll());
    }

    @Test
    void saveTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        User randomUser = User.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.save(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(Optional.empty(), daoUserFile.get(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserFile.get(randomUser.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoUserFile.get(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.get(User.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        assertEquals(Optional.empty(), daoUserFile.get(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        assertEquals(Optional.of(randomUser), daoUserFile.get(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoUserFile.get(User.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.get(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.remove(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.remove(randomUser.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoUserFile.remove(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.remove(User.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertFalse(daoUserFile.remove(User.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        User randomUser = User.getRandom();
        daoUserFile.save(randomUser);
        assertTrue(daoUserFile.remove(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoUserFile.remove(User.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.remove(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        List<User> users2 = User.getRandom(100);
        daoUserFile.saveAll(users2);
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(users);
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoUserFile.saveAll(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(daoUserFile.getAll().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), daoUserFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoUserFile daoUserFile = new DaoUserFile(file);
        assertEquals(1, daoUserFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoUserFile daoUserFile = new DaoUserFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoUserFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}