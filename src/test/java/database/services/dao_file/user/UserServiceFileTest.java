package database.services.dao_file.user;

import database.dao.DaoUserFile;
import database.services.UserService;
import entities.User;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceFileTest {

    List<User> users = User.getRandom(100);
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
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.of(users), fs.getAllUsers());
    }

    @Test
    void getAllUsersTest2() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), fs.getAllUsers());
    }

    @Test
    void getAllUsersTest3() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getAllUsers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllUsersToTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        List<User> users2 = User.getRandom(100);
        fs.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), fs.getAllUsers());
    }

    @Test
    void setAllUsersToTest2() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        List<User> users2 = User.getRandom(100);
        fs.setAllUsersTo(users2);
        assertEquals(Optional.of(users2), fs.getAllUsers());
    }

    @Test
    void setAllUsersToTest3() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        List<User> users2 = User.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.setAllUsersTo(users2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        List<User> usersCopy = new ArrayList<>(users);
        usersCopy.add(randomUser);
        assertEquals(Optional.of(usersCopy), fs.getAllUsers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertEquals(Optional.of(List.of(randomUser)), fs.getAllUsers());
    }

    @Test
    void saveTest3() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        User randomUser = User.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), fs.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), fs.getUser(randomUser.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), fs.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getUser(User.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        assertEquals(Optional.empty(), fs.getUser(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), fs.getUser(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), fs.getUser(User.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithUsernamePsswrdTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), fs.getUser(User.getRandom().getId()));
    }

    @Test
    void getWithUsernamePsswrdTest2() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), fs.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest3() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        assertEquals(Optional.empty(), fs.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest4() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        User randomUser = User.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getUser(randomUser.getUsername(), randomUser.getPassword()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void removeWithIdTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        assertFalse(fs.removeUser(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertTrue(fs.removeUser(randomUser.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertFalse(fs.removeUser(User.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeUser(User.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        assertFalse(fs.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertTrue(fs.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertFalse(fs.removeUser(User.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeUser(User.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void containsTest1() {
        UserService fs = new UserService(new DaoUserFile(file));
        assertFalse(fs.contains(User.getRandom().getUsername()));
    }

    @Test
    void containsTest2() {
        UserService fs = new UserService(new DaoUserFile(file));
        User randomUser = User.getRandom();
        fs.saveUser(randomUser);
        assertTrue(fs.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(fs.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        UserService fs = new UserService(new DaoUserFile(file));
        assertEquals(1, fs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        UserService fs = new UserService(new DaoUserFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}