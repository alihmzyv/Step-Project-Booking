package database.dao;

import entities.Identifiable;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A object);
    Optional<A> get(int id);
    Optional<A> get(A object);
    boolean remove(int id);
    boolean remove(A object);
    Optional<List<A>> getAll();
    void saveAll(List<A> objects);
    void setAllTo(List<A> data);
    boolean isPresent();
    boolean isEmpty();
    int getMaxId();
}
