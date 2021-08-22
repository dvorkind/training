package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.UserDao;
import by.dvorkin.web.model.dao.impl.AccountDaoImpl;
import by.dvorkin.web.model.dao.impl.UserDaoImpl;
import by.dvorkin.web.model.pool.ConnectionPool;
import by.dvorkin.web.model.pool.ConnectionPoolException;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.UserService;
import by.dvorkin.web.model.service.exceptions.FactoryException;

import java.sql.Connection;

public class ServiceFactoryImpl implements ServiceFactory {
    private Connection connection;
    private AccountDao accountDao = null;
    private UserDao userDao = null;
    private AccountService accountService = null;
    private UserService userService = null;
    private Transaction transaction = null;

    @Override
    public AccountService getAccountService() throws FactoryException {
        if (accountService == null) {
            AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
            accountServiceImpl.setTransaction(getTransaction());
            accountServiceImpl.setAccountDao(getAccountDao());
            accountServiceImpl.setUserDao(getUserDao());
            accountService = accountServiceImpl;
        }
        return accountService;
    }

    @Override
    public UserService getUserService() throws FactoryException {
        if (userService == null) {
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            userServiceImpl.setTransaction(getTransaction());
            userServiceImpl.setUserDao(getUserDao());
            userService = userServiceImpl;
        }
        return userService;
    }

    @Override
    public AccountDao getAccountDao() throws FactoryException {
        if (accountDao == null) {
            AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
            accountDaoImpl.setConnection(getConnection());
            accountDao = accountDaoImpl;
        }
        return accountDao;
    }

    @Override
    public UserDao getUserDao() throws FactoryException {
        if (userDao == null) {
            UserDaoImpl userDaoImpl = new UserDaoImpl();
            userDaoImpl.setConnection(getConnection());
            userDao = userDaoImpl;
        }
        return userDao;
    }

    @Override
    public Transaction getTransaction() throws FactoryException {
        if (transaction == null) {
            TransactionImpl transactionImpl = new TransactionImpl();
            transactionImpl.setConnection(getConnection());
            transaction = transactionImpl;
        }
        return transaction;
    }

    @Override
    public Connection getConnection() throws FactoryException {
        if (connection == null) {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (ConnectionPoolException e) {
                throw new FactoryException(e);
            }
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (Exception ignored) {
        }
    }
}
