package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public class SubscriberServiceImpl implements SubscriberService {
    private SubscriberDao subscriberDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setSubscriberDao(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    @Override
    public Subscriber findByAccountId(Long id) throws ServiceException {
        try {
            return subscriberDao.readByAccountId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subscriber> getAll() throws ServiceException {
        try {
            return subscriberDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subscriber> getNewSubscribers() throws ServiceException {
        try {
            return subscriberDao.readNewSubscribers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void activate(int id) throws ServiceException {
        try {
            transaction.start();
            subscriberDao.activate(id);
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e);
        }
    }
}
