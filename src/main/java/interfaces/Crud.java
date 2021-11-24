package interfaces;

import java.util.List;

public interface Crud<T> {
    T get(Long id);
    List<T> getAll();
    Boolean create(T obj);
    Boolean update(Long id, T obj);
    Boolean delete(Long id);
}
