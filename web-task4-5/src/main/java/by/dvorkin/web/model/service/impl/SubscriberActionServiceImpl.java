package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberActionNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<SubscriberAction> getActionsBetweenDates(Long subscriberId, Date dateBefore, Date dateAfter) throws ServiceException {
        try {
            List<SubscriberAction> actions;
            if (dateAfter.after(dateBefore)) {
                actions = subscriberActionDao.readBetweenDates(subscriberId, dateBefore, dateAfter);
            } else {
                actions = subscriberActionDao.readBetweenDates(subscriberId, dateAfter, dateBefore);
            }
            if (actions.size() == 0) {
                throw new SubscriberActionNotFoundException(String.valueOf(subscriberId));
            }
            return actions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Date getLastChangeTariff(Long subscriberId) throws ServiceException {
        try {
            return subscriberActionDao.readLastChangeTariff(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
