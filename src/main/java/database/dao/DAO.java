package database.dao;

import entities.Identifiable;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A obj);
    Optional<A> get(int id);
    Optional<A> get(A obj);
    boolean remove(int id);
    boolean remove(A obj);
    Optional<List<A>> getAll();
    void saveAll(List<A> obj);
    void setAll(List<A> data);
    boolean isPresent();
    boolean isEmpty();
    void requiresNonNull();
    int getMaxId();
}
