package database.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A object);
    Optional<A> get(int id);
    void saveAll(List<A> objects);
    Optional<List<A>> getAll();
    void setAll(List<A> data);
    boolean remove(int id);
    boolean isPresent();
    boolean isEmpty();
    int getMaxId();
}
