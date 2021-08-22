package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Account;

public interface AccountDao extends Dao<Account> {
    Account readByLogin(String login) throws DaoException;

    Account readByLoginAndPassword(String login, String password) throws DaoException;
}
