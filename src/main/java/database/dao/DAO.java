package database.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    Optional<A> get(int id);
    Optional<A> get(A object);
    void save(A object);
    boolean remove(int id);
    boolean remove(A object);
    Optional<List<A>> getAll();
    void saveAll(List<A> objects);
    void setAll(List<A> data);
    boolean isPresent();
    boolean isEmpty();
    int getMaxId();
}
