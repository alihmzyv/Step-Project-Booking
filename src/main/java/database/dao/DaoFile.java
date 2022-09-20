package database.dao;

import entities.Identifiable;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoFile<A extends Identifiable> implements DAO<A> {

    private final File file;


    //constructors
    public DaoFile(File file) {
        this.file = file;
    }


    //methods
    @Override
    public void save(A object) {
        List<A> data = getAll().orElseGet(ArrayList::new);
        data.add(object);
        setAll(data);
    }

    @Override
    public Optional<A> get(int id) {
        requiresNonNull();
        return getAll().get().stream()
                .filter(obj -> obj.getId() == id)
                .findAny();
    }

    @Override
    public Optional<A> get(A object) {
        requiresNonNull();
        return getAll().get().stream()
                .filter(obj -> obj.equals(object))
                .findAny();
    }

    @Override
    public boolean remove(int id) {
        requiresNonNull();
        List<A> data = getAll().get();
        if (data.removeIf(obj -> obj.getId() == id)) {
            setAll(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(A object) {
        requiresNonNull();
        List<A> data = getAll().get();
        if (data.remove(object)) {
            setAll(data);
            return true;
        }
        return false;
    }


    @Override
    public void saveAll(List<A> objects) {
        List<A> data = getAll().orElseGet(ArrayList::new);
        data.addAll(objects);
        setAll(data);
    }

    @SuppressWarnings("unchecked")
    public Optional<List<A>> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((List<A>) ois.readObject());
        }
        catch (EOFException exc) {
            return Optional.empty();
        }
        catch (ClassNotFoundException | IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    public void setAll(List<A> data) {
        if (!file.exists()) {
            throw new LocalDatabaseException(
                    new FileNotFoundException(String.format("File not found at: %s", file.getAbsolutePath())));
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    @Override
    public boolean isPresent() {
        return getAll().isPresent();
    }

    @Override
    public boolean isEmpty() {
        return getAll().isEmpty();
    }

    @Override
    public void requiresNonNull() {
        getAll().orElseThrow(() -> new NonInstantiatedDaoException("The file field of Dao is an empty file or a file not containing any list of the corresponding entity."));
    }

    @Override
    public int getMaxId() {
        return getAll().orElseGet(ArrayList::new).stream()
                .map(Identifiable::getId)
                .mapToInt(id -> id)
                .max()
                .orElse(1);
    }
}
