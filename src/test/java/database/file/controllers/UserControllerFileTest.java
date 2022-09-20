package database.file.controllers;

import database.controllers.UserController;
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

public class UserControllerFileTest {
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
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.of(randomUsers), uc.getAllUsers());
    }

    @Test
    void getAllUsersTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getAllUsers());
    }

    @Test
    void getAllUsersTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                uc::getAllUsers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllUsersToTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        List<User> randomUsers2 = User.getRandom(100);
        uc.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), uc.getAllUsers());
    }

    @Test
    void setAllUsersToTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        List<User> randomUsers2 = User.getRandom(100);
        uc.setAllUsersTo(randomUsers2);
        assertEquals(Optional.of(randomUsers2), uc.getAllUsers());
    }

    @Test
    void setAllUsersToTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        List<User> randomUsers2 = User.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.setAllUsersTo(randomUsers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        List<User> randomUsersCopy = new ArrayList<>(randomUsers);
        randomUsersCopy.add(randomUser);
        assertEquals(Optional.of(randomUsersCopy), uc.getAllUsers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        assertEquals(Optional.of(List.of(randomUser)), uc.getAllUsers());
    }

    @Test
    void saveTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.saveUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), uc.getUser(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> uc.getUser(User.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.getUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithUsernamePsswrdTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.getUser(randomUser.getUsername(), randomUser.getPassword()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertFalse(uc.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        assertTrue(uc.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> uc.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.removeUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void containsTest1() {
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertFalse(uc.contains(User.getRandom().getUsername()));
    }

    @Test
    void containsTest2() {
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(randomUser);
        assertTrue(uc.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(uc.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), uc.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(1, uc.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                uc::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}
