package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.BillDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;

import java.time.LocalDateTime;
import java.util.List;

public class BillServiceImpl implements BillService {
    private BillDao billDao;
    private SubscriberDao subscriberDao;
    private SubscriberActionDao subscriberActionDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
    }

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    public void setSubscriberDao(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            transaction.start();
            billDao.delete(id);
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
    public List<Bill> getAllSubscribersBill(Long subscriberId) throws ServiceException {
        try {
            return billDao.readAllSubscribersBill(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Bill> getAllUnpaid() throws ServiceException {
        try {
            return billDao.readAllUnpaid();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void payBill(Long id, Subscriber subscriber) throws ServiceException {
        try {
            transaction.start();
            Bill bill = billDao.read(id);
            if (subscriber.getBalance() - bill.getSum() >= 0) {
                bill.setPaid(true);
                billDao.update(bill);
                subscriber.setBalance(subscriber.getBalance() - bill.getSum());
                if (billDao.readAllUnpaidSubscribersBill(subscriber.getId())
                        .size() == 0 && subscriber.getBalance() >= 0) {
                    subscriber.setBlocked(false);
                }
                subscriberDao.update(subscriber);
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), -bill.getSum());
                subscriberActionDao.create(subscriberAction);
            } else {
                throw new SubscriberNotEnoughMoneyException(String.valueOf(bill.getSum()));
            }
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e.getMessage());
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }

    private SubscriberAction createSubscriberAction(Long subscriberId, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(Action.PAY_BILL);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(LocalDateTime.now());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }
}
