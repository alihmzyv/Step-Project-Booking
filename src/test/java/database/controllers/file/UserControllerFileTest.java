package database.controllers.file;

import database.controllers.UserController;
import database.dao.DaoUserFile;
import database.services.UserService;
import entities.User;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerFileTest {
    List<User> users = User.getRandom(100);
    User user = User.getRandom();
    private final File file = new File("src/test/java/database/services/dao_file/user/users.bin");
    private final File fileNonExisting = new File("src/test/java/database/services/dao_file/user/none.bin");

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
    void getAllUsersTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.of(users), uc.getAllUsers());
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
        List<User> users2 = User.getRandom(100);
        uc.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), uc.getAllUsers());
    }

    @Test
    void setAllUsersToTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        List<User> users2 = User.getRandom(100);
        uc.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), uc.getAllUsers());
    }

    @Test
    void setAllUsersToTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        List<User> users2 = User.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.setAllUsersTo(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        List<User> usersCopy = new ArrayList<>(users);
        usersCopy.add(user);
        assertEquals(Optional.of(usersCopy), uc.getAllUsers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        assertEquals(Optional.of(List.of(user)), uc.getAllUsers());
    }

    @Test
    void saveTest3() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.saveUser(user));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        assertEquals(Optional.of(user), uc.getUser(user.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.getUser(User.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(user));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        assertEquals(Optional.of(user), uc.getUser(user));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(User.getRandom()));
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
        assertEquals(Optional.empty(), uc.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithUsernamePsswrdTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        assertEquals(Optional.of(user), uc.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertEquals(Optional.empty(), uc.getUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.getUser(user.getUsername(), user.getPassword()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void removeWithIdTest1() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertFalse(uc.removeUser(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        uc.saveUser(user);
        assertTrue(uc.removeUser(user.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertFalse(uc.removeUser(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        UserService uc = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> uc.removeUser(User.getRandom().getId()));
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
        uc.saveUser(user);
        assertTrue(uc.removeUser(user));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        UserController uc = new UserController(new UserService(new DaoUserFile(file)));
        assertFalse(uc.removeUser(User.getRandom()));
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
        uc.saveUser(user);
        assertTrue(uc.contains(user.getUsername()));
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
