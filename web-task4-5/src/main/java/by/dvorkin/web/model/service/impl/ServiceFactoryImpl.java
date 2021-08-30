package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.dao.impl.AccountDaoImpl;
import by.dvorkin.web.model.dao.impl.ServiceDaoImpl;
import by.dvorkin.web.model.dao.impl.SubscriberActionDaoImpl;
import by.dvorkin.web.model.dao.impl.TariffDaoImpl;
import by.dvorkin.web.model.dao.impl.SubscriberDaoImpl;
import by.dvorkin.web.model.pool.ConnectionPool;
import by.dvorkin.web.model.pool.ConnectionPoolException;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;

import java.sql.Connection;

public class ServiceFactoryImpl implements ServiceFactory {
    private Connection connection;
    private AccountDao accountDao = null;
    private SubscriberDao subscriberDao = null;
    private TariffDao tariffDao = null;
    private ServiceDao serviceDao = null;
    private SubscriberActionDao subscriberActionDao = null;
    private AccountService accountService = null;
    private SubscriberService subscriberService = null;
    private TariffService tariffService = null;
    private ServiceService serviceService = null;
    private SubscriberActionService subscriberActionService = null;
    private Transaction transaction = null;

    @Override
    public AccountService getAccountService() throws FactoryException {
        if (accountService == null) {
            AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
            accountServiceImpl.setTransaction(getTransaction());
            accountServiceImpl.setAccountDao(getAccountDao());
            accountServiceImpl.setSubscriberDao(getSubscriberDao());
            accountServiceImpl.setSubscriberActionDao(getSubscriberActionDao());
            accountService = accountServiceImpl;
        }
        return accountService;
    }

    @Override
    public SubscriberService getSubscriberService() throws FactoryException {
        if (subscriberService == null) {
            SubscriberServiceImpl subscriberServiceImpl = new SubscriberServiceImpl();
            subscriberServiceImpl.setTransaction(getTransaction());
            subscriberServiceImpl.setSubscriberDao(getSubscriberDao());
            subscriberService = subscriberServiceImpl;
        }
        return subscriberService;
    }

    @Override
    public TariffService getTariffService() throws FactoryException {
        if (tariffService == null) {
            TariffServiceImpl tariffServiceImpl = new TariffServiceImpl();
            tariffServiceImpl.setTransaction(getTransaction());
            tariffServiceImpl.setTariffDao(getTariffDao());
            tariffService = tariffServiceImpl;
        }
        return tariffService;
    }

    @Override
    public ServiceService getServiceService() throws FactoryException {
        if (serviceService == null) {
            ServiceServiceImpl serviceServiceImpl = new ServiceServiceImpl();
            serviceServiceImpl.setTransaction(getTransaction());
            serviceServiceImpl.setServiceDao(getServiceDao());
            serviceService = serviceServiceImpl;
        }
        return serviceService;
    }

    @Override
    public SubscriberActionService getSubscriberActionService() throws FactoryException {
        if (subscriberActionService == null) {
            SubscriberActionServiceImpl subscriberActionServiceImpl = new SubscriberActionServiceImpl();
            subscriberActionServiceImpl.setTransaction(getTransaction());
            subscriberActionServiceImpl.setSubscriberActionDao(getSubscriberActionDao());
            subscriberActionService = subscriberActionServiceImpl;
        }
        return subscriberActionService;
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
    public SubscriberDao getSubscriberDao() throws FactoryException {
        if (subscriberDao == null) {
            SubscriberDaoImpl subscriberDaoImpl = new SubscriberDaoImpl();
            subscriberDaoImpl.setConnection(getConnection());
            subscriberDao = subscriberDaoImpl;
        }
        return subscriberDao;
    }

    @Override
    public TariffDao getTariffDao() throws FactoryException {
        if (tariffDao == null) {
            TariffDaoImpl tariffDaoImpl = new TariffDaoImpl();
            tariffDaoImpl.setConnection(getConnection());
            tariffDao = tariffDaoImpl;
        }
        return tariffDao;
    }

    @Override
    public ServiceDao getServiceDao() throws FactoryException {
        if (serviceDao == null) {
            ServiceDaoImpl serviceDaoImpl = new ServiceDaoImpl();
            serviceDaoImpl.setConnection(getConnection());
            serviceDao = serviceDaoImpl;
        }
        return serviceDao;
    }

    @Override
    public SubscriberActionDao getSubscriberActionDao() throws FactoryException {
        if (subscriberActionDao == null) {
            SubscriberActionDaoImpl subscriberActionDaoImpl = new SubscriberActionDaoImpl();
            subscriberActionDaoImpl.setConnection(getConnection());
            subscriberActionDao = subscriberActionDaoImpl;
        }
        return subscriberActionDao;
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
