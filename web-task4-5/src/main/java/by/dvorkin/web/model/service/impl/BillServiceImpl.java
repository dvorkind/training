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

import java.util.Date;
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
    public void create(Bill bill) throws ServiceException {
        try {
            transaction.start();
            bill.setId(billDao.create(bill));
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
    public Bill getById(Long id) throws ServiceException {
        try {
            return billDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Date getLastBill(Long subscriberId) throws ServiceException {
        try {
            return billDao.readLastBill(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Bill> getAll(Long subscriberId) throws ServiceException {
        try {
            return billDao.readAll(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Bill> getAllPaid(Long subscriberId) throws ServiceException {
        try {
            return billDao.readAllPaid(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Bill> getAllUnpaid(Long subscriberId) throws ServiceException {
        try {
            return billDao.readAllUnpaid(subscriberId);
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
                if (billDao.readAllUnpaid(subscriber.getId()).size() == 0 && subscriber.getBalance() >= 0) {
                    subscriber.setBlocked(false);
                }
                subscriberDao.update(subscriber);
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), bill.getSum());
                subscriberActionDao.update(subscriberAction);
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
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }
}
