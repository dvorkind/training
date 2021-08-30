package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Date;

public class SubscriberActionServiceImpl implements SubscriberActionService {
    private SubscriberActionDao subscriberActionDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    @Override
    public void create(SubscriberAction subscriberAction) throws ServiceException {
        try {
            transaction.start();
            subscriberAction.setId(subscriberActionDao.create(subscriberAction));
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e);
        }
    }

    @Override
    public Date getLastChangeTariff(Long subscriberId) throws ServiceException {
        try {
            return subscriberActionDao.readLastChangeTariff(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
