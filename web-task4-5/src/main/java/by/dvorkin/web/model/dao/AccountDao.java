package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Account;

public interface AccountDao extends Dao<Account> {
    /**
     * Method gets Account by login
     *
     * @param login parameter value
     * @return <code>Account</code> object or null
     * @throws DaoException in case of exception
     */
    Account readByLogin(String login) throws DaoException;

    /**
     * Method gets Account by login and password used for authorization
     *
     * @param login    parameter value
     * @param password parameter value
     * @return <code>Account</code> object or null
     * @throws DaoException in case of exception
     */
    Account readByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Method hashes the password
     *
     * @param sourcePassword parameter value
     * @return hash code of the source string
     */
    String passwordToSHA(String sourcePassword);
}
