package database.dao;

import entities.Identifiable;
import exceptions.database_exceptions.NonInstantiatedDaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoInMemory<A extends Identifiable> implements DAO<A>{
    private List<A> data;


    //constructors
    public DaoInMemory(List<A> data) {
        this.data = new ArrayList<>(data);
    }


    //methods
    @Override
    public void save(A object) {
        if (isEmpty()) {
            data = new ArrayList<>();
        }
        data.add(object);
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
        return data.removeIf(obj -> obj.getId() == id);
    }

    @Override
    public boolean remove(A object) {
        requiresNonNull();
        return data.remove(object);
    }

    @Override
    public Optional<List<A>> getAll() {
        return Optional.ofNullable(data);
    }

    @Override
    public void saveAll(List<A> objects) {
        if (isEmpty()) {
            data = new ArrayList<>();
        }
        data.addAll(objects);
    }

    @Override
    public void setAll(List<A> data) {
        this.data = new ArrayList<>(data);
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
        getAll().orElseThrow(() -> new NonInstantiatedDaoException("The data field of Dao is null."));
    }
    @Override
    public int getMaxId() {
        return getAll().orElseGet(ArrayList::new).stream()
                .map(Identifiable::getId)
                .mapToInt(id -> id)
                .max()
                .orElse(0);
    }
}
