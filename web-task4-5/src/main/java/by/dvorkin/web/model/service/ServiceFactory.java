package by.dvorkin.web.model.service;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.service.exceptions.FactoryException;

import java.sql.Connection;

public interface ServiceFactory extends AutoCloseable {
    AccountService getAccountService() throws FactoryException;

    SubscriberService getSubscriberService() throws FactoryException;

    TariffService getTariffService() throws FactoryException;

    ServiceService getServiceService() throws FactoryException;

    SubscriberActionService getSubscriberActionService() throws FactoryException;

    AccountDao getAccountDao() throws FactoryException;

    SubscriberDao getSubscriberDao() throws FactoryException;

    TariffDao getTariffDao() throws FactoryException;

    ServiceDao getServiceDao() throws FactoryException;

    SubscriberActionDao getSubscriberActionDao() throws FactoryException;

    Connection getConnection() throws FactoryException;

    Transaction getTransaction() throws FactoryException;
}
