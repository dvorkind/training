package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.UserDao;
import by.dvorkin.web.model.entity.User;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.UserService;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByAccountId(Long id) throws ServiceException {
        try {
            return userDao.readByAccountId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, User> findAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, User> findNewUsers() throws ServiceException {
        try {
            return userDao.readNewUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void activate(int id) throws ServiceException {
        try {
            userDao.activate(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
