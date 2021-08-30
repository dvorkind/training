package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.AccountPasswordIncorrectException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberPhoneNotUniqueException;

import java.util.Date;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private SubscriberDao subscriberDao;
    private SubscriberActionDao subscriberActionDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setSubscriberDao(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    @Override
    public Account login(String login, String password) throws ServiceException {
        try {
            return accountDao.readByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(Account account, Subscriber subscriber) throws ServiceException {
        try {
            transaction.start();
            if (accountDao.readByLogin(account.getLogin()) == null) {
                if (subscriberDao.readByPhoneNumber(subscriber.getPhoneNumber()) == null) {
                    Long id = accountDao.create(account);
                    account.setId(id);
                    subscriber.setAccountId(id);
                    id = subscriberDao.create(subscriber);
                    subscriber.setId(id);
                    SubscriberAction subscriberAction = createSubscriberAction();
                    subscriberAction.setSubscriberId(id);
                    subscriberActionDao.create(subscriberAction);
                } else {
                    throw new SubscriberPhoneNotUniqueException(subscriber.getPhoneNumber());
                }
            } else {
                throw new AccountLoginNotUniqueException(account.getLogin());
            }
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, Account account) throws ServiceException {
        try {
            transaction.start();
            if (account.getPassword().equals(accountDao.passwordToSHA(oldPassword))) {
                account.setPassword(accountDao.passwordToSHA(newPassword));
                accountDao.update(account);
            } else {
                throw new AccountPasswordIncorrectException(account.getId());
            }
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }

    private SubscriberAction createSubscriberAction() {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(Action.REGISTRATION);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(0);
        return subscriberAction;
    }
}
