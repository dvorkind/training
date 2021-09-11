package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.BillDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberCanNotChangeTariffException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotExistException;

import java.util.Date;
import java.util.List;

public class SubscriberServiceImpl implements SubscriberService {
    private static final long DAY_IN_MILLISECONDS = 86400000L;
    private static final int TARIFF_CHANGE_COST = 100;
    private SubscriberDao subscriberDao;
    private SubscriberActionDao subscriberActionDao;
    private ServiceDao serviceDao;
    private BillDao billDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    public void setSubscriberDao(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public Subscriber getByAccountId(Long id) throws ServiceException {
        try {
            return subscriberDao.readByAccountId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Subscriber> getAll() throws ServiceException {
        try {
            return subscriberDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Subscriber> getNewSubscribers() throws ServiceException {
        try {
            return subscriberDao.readNewSubscribers();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Subscriber> getDebtors() throws ServiceException {
        try {
            return subscriberDao.readDebtors();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Subscriber subscriber) throws ServiceException {
        try {
            transaction.start();
            subscriberDao.update(subscriber);
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

    @Override
    public void subtractBalance(Subscriber subscriber, int sum, Action action) throws ServiceException {
        try {
            if (subscriber.getBalance() >= 0) {
                subscriber.setBalance(subscriber.getBalance() - sum);
                subscriberDao.update(subscriber);
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), action, sum);
                subscriberActionDao.create(subscriberAction);
            } else {
                throw new SubscriberNotEnoughMoneyException(String.valueOf(sum));
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addingBalance(Subscriber subscriber, int sum) throws ServiceException {
        try {
            if (sum > 0) {
                transaction.start();
                subscriber.setBalance(subscriber.getBalance() + sum);
                if (billDao.readAllUnpaid(subscriber.getId()).size() == 0 && subscriber.getBalance() >= 0) {
                    subscriber.setBlocked(false);
                }
                subscriberDao.update(subscriber);
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), Action.REFILL_BALANCE,
                        sum);
                subscriberActionDao.create(subscriberAction);
                transaction.commit();
            }
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

    @Override
    public Subscriber getById(Long id) throws ServiceException {
        try {
            return subscriberDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Subscriber getByPhoneNumber(String phoneNumber) throws ServiceException {
        try {
            Subscriber subscriber = subscriberDao.readByPhoneNumber(phoneNumber);
            if (subscriber != null) {
                return subscriber;
            } else {
                throw new SubscriberNotExistException(phoneNumber);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void switchOnService(Subscriber subscriber, Long serviceId) throws ServiceException {
        try {
            transaction.start();
            Service service = serviceDao.read(serviceId);
            subtractBalance(subscriber, service.getPrice(), Action.ADD_SERVICE);
            serviceDao.switchOn(subscriber.getId(), serviceId);
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

    @Override
    public void switchOffService(Long subscriberId, Long serviceId) throws ServiceException {
        try {
            transaction.start();
            serviceDao.switchOff(subscriberId, serviceId);
            SubscriberAction subscriberAction = createSubscriberAction(subscriberId, Action.DELETE_SERVICE, 0);
            subscriberActionDao.create(subscriberAction);
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

    @Override
    public void changeTariff(Subscriber subscriber, Long tariffId) throws ServiceException {
        try {
            transaction.start();
            Date dateNow = new Date();
            Date dateLast = subscriberActionDao.readLastChangeTariff(subscriber.getId());
            if (dateLast.getTime() > dateNow.getTime() || (dateNow.getTime() - dateLast.getTime()) > DAY_IN_MILLISECONDS) {
                subscriber.setTariff(tariffId);
                subtractBalance(subscriber, TARIFF_CHANGE_COST, Action.CHANGE_TARIFF);
            } else {
                throw new SubscriberCanNotChangeTariffException(String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",
                        new Date(dateLast.getTime() + DAY_IN_MILLISECONDS)));
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

    @Override
    public void setBlock(Long id) throws ServiceException {
        Subscriber subscriber = getById(id);
        subscriber.setBlocked(true);
        update(subscriber);
    }

    @Override
    public void activate(Long id) throws ServiceException {
        Subscriber subscriber = getById(id);
        subscriber.setRegistered(true);
        update(subscriber);
    }

    private SubscriberAction createSubscriberAction(Long subscriberId, Action action, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(action);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }
}
