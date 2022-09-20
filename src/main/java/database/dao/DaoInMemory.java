package database.dao;

import entities.Identifiable;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoInMemory<A extends Identifiable> implements DAO<A>{

    private List<A> objects;

    public DaoInMemory(List<A> objects) {
        this.objects = new ArrayList<>(objects);
    }

    @Override
    public void save(A object) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        objects.add(object);
    }

    @Override
    public Optional<A> get(int id) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        return getAll().get().stream()
                .filter(obj -> obj.getId() == id)
                .findAny();
    }

    @Override
    public Optional<A> get(A object) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        return getAll().get().stream()
                .filter(obj -> obj.equals(object))
                .findAny();
    }

    @Override
    public boolean remove(int id) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        return objects.removeIf(obj -> obj.getId() == id);
    }

    @Override
    public boolean remove(A object) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        return objects.remove(object);
    }

    @Override
    public Optional<List<A>> getAll() {
        return Optional.ofNullable(objects);
    }

    @Override
    public void saveAll(List<A> objects) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("List field of DAO is null");
        }
        this.objects.addAll(objects);
    }

    @Override
    public void setAllTo(List<A> data) {
        objects = new ArrayList<>(data);
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
    public int getMaxId() {
        return getAll().orElseGet(ArrayList::new).stream()
                .map(Identifiable::getId)
                .mapToInt(id -> id)
                .max()
                .orElse(1);
    }
}
