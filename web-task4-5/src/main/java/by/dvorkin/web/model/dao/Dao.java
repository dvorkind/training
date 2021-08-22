package by.dvorkin.web.model.dao;

public interface Dao<T> {
    Long create(T t) throws DaoException;
}
