package by.dvorkin.web.model.service;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.UserDao;
import by.dvorkin.web.model.service.exceptions.FactoryException;

import java.sql.Connection;

public interface ServiceFactory extends AutoCloseable {
    AccountService getAccountService() throws FactoryException;

    UserService getUserService() throws FactoryException;

    AccountDao getAccountDao() throws FactoryException;

    UserDao getUserDao() throws FactoryException;

    Connection getConnection() throws FactoryException;

    Transaction getTransaction() throws FactoryException;
}
