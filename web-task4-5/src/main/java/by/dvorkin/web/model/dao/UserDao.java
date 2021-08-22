package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.User;

import java.util.Map;

public interface UserDao extends Dao<User> {
    Map<String, User> readAll() throws DaoException;

    Map<String, User> readNewUsers() throws DaoException;

    User readByPhoneNumber(String phoneNumber) throws DaoException;

    User readByAccountId(Long id) throws DaoException;

    void activate(int id) throws DaoException;

}
