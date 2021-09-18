package by.dvorkin.web.model.dao;

public interface Dao<T> {
    /**
     * Method writes the passed object
     *
     * @param t Entity class object
     * @return record number of the passed object in <code>Long</code>
     * @throws DaoException in case of exception
     */
    Long create(T t) throws DaoException;

    /**
     * Method reads the object by id
     *
     * @param id parameter value
     * @return Entity class object
     * @throws DaoException in case of exception
     */
    T read(Long id) throws DaoException;

    /**
     * Method updates the passed object
     *
     * @param t Entity class object
     * @throws DaoException in case of exception
     */
    void update(T t) throws DaoException;

    /**
     * Method deletes the object by id
     *
     * @param id parameter value
     * @throws DaoException in case of exception
     */
    void delete(Long id) throws DaoException;
}
