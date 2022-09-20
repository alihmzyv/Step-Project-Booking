package database.file.service.user;

import database.dao.DaoUserFile;
import database.services.UserService;
import entities.User;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceFileTest {

    List<User> randomUsers = User.getRandom(100);
    User randomUser = User.getRandom();
    private final File file = new File("src/test/java/database/file/files/users.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomUsers);
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
    void getAllUsersTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.of(randomUsers), us.getAllUsers());
    }

    @Test
    void getAllUsersTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getAllUsers());
    }

    @Test
    void getAllUsersTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                us::getAllUsers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllUsersToTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        List<User> randomUsers2 = User.getRandom(100);
        us.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), us.getAllUsers());
    }

    @Test
    void setAllUsersToTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        List<User> randomUsers2 = User.getRandom(100);
        us.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), us.getAllUsers());
    }

    @Test
    void setAllUsersToTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        List<User> randomUsers2 = User.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> us.setAllUsersTo(randomUsers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        List<User> randomUsersCopy = new ArrayList<>(randomUsers);
        randomUsersCopy.add(randomUser);
        assertEquals(Optional.of(randomUsersCopy), us.getAllUsers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertEquals(Optional.of(List.of(randomUser)), us.getAllUsers());
    }

    @Test
    void saveTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> us.saveUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getUser(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> us.getUser(User.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> us.getUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithUsernamePsswrdTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> us.getUser(randomUser.getUsername(), randomUser.getPassword()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void removeWithObjTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertFalse(us.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> us.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> us.removeUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void containsTest1() {
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> us.contains(User.getRandom().getUsername()));
    }

    @Test
    void containsTest2() {
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(us.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), us.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(1, us.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                us::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}