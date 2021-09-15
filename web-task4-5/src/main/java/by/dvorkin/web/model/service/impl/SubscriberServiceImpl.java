package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.BillDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.BillTooEarlyException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberCanNotChangeTariffException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SubscriberServiceImpl implements SubscriberService {
    private SubscriberDao subscriberDao;
    private SubscriberActionDao subscriberActionDao;
    private ServiceDao serviceDao;
    private TariffDao tariffDao;
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

    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
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
                SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), action, -sum);
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
                if (billDao.readAllUnpaidSubscribersBill(subscriber.getId())
                        .size() == 0 && subscriber.getBalance() >= 0) {
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
    public void switchOnService(Subscriber subscriber, Long serviceId) throws ServiceException {
        try {
            transaction.start();
            SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), Action.ADD_SERVICE, 0);
            subscriberActionDao.create(subscriberAction);
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
            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime dateLast = subscriberActionDao.readLastChangeTariff(subscriber.getId());
            if (dateLast == null) {
                dateLast = subscriberActionDao.readSubscriberRegistrationDate(subscriber.getId());
            }
            Duration duration1 = Duration.between(dateLast, dateNow);
            Duration duration2 = Duration.ofHours(24);
            if (duration1.compareTo(duration2) > 0) {
                changeTariffByAdmin(subscriber, tariffId);
            } else {
                throw new SubscriberCanNotChangeTariffException(String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",
                        dateLast.plusDays(1)));
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeTariffByAdmin(Subscriber subscriber, Long tariffId) throws ServiceException {
        try {
            transaction.start();
            subscriber.setTariff(tariffId);
            SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), Action.CHANGE_TARIFF, 0);
            subscriberActionDao.create(subscriberAction);
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

    @Override
    public void issueBill(Long id) throws ServiceException {
        try {
            transaction.start();
            if (issue(id)) {
                transaction.commit();
            } else {
                throw new BillTooEarlyException(String.valueOf(id));
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
    public int issueBillToAll() throws ServiceException {
        try {
            transaction.start();
            List<Subscriber> subscribers = subscriberDao.readAll();
            int successIssue = 0;
            for (Subscriber subscriber : subscribers) {
                if (issue(subscriber.getId())) {
                    successIssue++;
                }
            }
            transaction.commit();
            return successIssue;
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

    private boolean issue(Long id) throws DaoException {
        LocalDateTime lastDate = billDao.readLastBill(id);
        if (lastDate == null) {
            lastDate = subscriberActionDao.readSubscriberRegistrationDate(id);
        }
        LocalDateTime nowDate = LocalDateTime.now();
        int daysAfterLastBill = (int) ChronoUnit.DAYS.between(lastDate, nowDate);
        if (daysAfterLastBill > 0) {
            List<Service> servicesList = serviceDao.readSubscribersService(id);
            Subscriber subscriber = subscriberDao.read(id);
            int sumServicesFee = servicesList.stream().mapToInt(Service::getPrice).sum();
            int sumTariff = tariffDao.read(subscriber.getTariff()).getSubscriptionFee();
            int dailyFee = (sumServicesFee + sumTariff) / 30;
            int billSum = dailyFee * daysAfterLastBill;
            billDao.create(createBill(id, billSum));
            return true;
        } else {
            return false;
        }
    }

    private SubscriberAction createSubscriberAction(Long subscriberId, Action action, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(action);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(LocalDateTime.now());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }

    private Bill createBill(Long subscriberId, int sum) {
        Bill bill = new Bill();
        bill.setSubscriberId(subscriberId);
        bill.setInvoiceDate(LocalDateTime.now());
        bill.setSum(sum);
        bill.setPaid(false);
        return bill;
    }
}
