package by.dvorkin.web.model.dao;

public interface Dao<T> {
    Long create(T t) throws DaoException;

    void update(T t) throws DaoException;

    T read(Long id) throws DaoException;
}
