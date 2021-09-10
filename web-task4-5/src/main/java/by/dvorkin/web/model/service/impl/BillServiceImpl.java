package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.BillDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;

import java.util.Date;
import java.util.List;

public class BillServiceImpl implements BillService {
    private BillDao billDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setBilDao(BillDao billDao) {
        this.billDao = billDao;
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
    public int payBill(Long id, Subscriber subscriber) throws ServiceException {
        try {
            transaction.start();
            Bill bill = billDao.read(id);
            if (subscriber.getBalance() - bill.getSum() >= 0) {
                bill.setPaid(true);
                billDao.update(bill);
                subscriber.setBalance(subscriber.getBalance() - bill.getSum());
                if (billDao.readAllUnpaid(subscriber.getId()) != null) {
                    subscriber.setBlocked(false);
                }

            } else {
                throw new SubscriberNotEnoughMoneyException(String.valueOf(bill.getSum()));
            }
            transaction.commit();
            return bill.getSum();
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
}
