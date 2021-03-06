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
import by.dvorkin.web.model.service.exceptions.AccountNotExistException;
import by.dvorkin.web.model.service.exceptions.AccountPasswordIncorrectException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotExistException;
import by.dvorkin.web.model.service.exceptions.SubscriberPhoneNotUniqueException;

import java.time.LocalDateTime;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private SubscriberDao subscriberDao;
    private SubscriberActionDao subscriberActionDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setSubscriberDao(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    @Override
    public Account login(String login, String password) throws ServiceException {
        try {
            Account account = accountDao.readByLoginAndPassword(login, password);
            if (account != null) {
                return account;
            } else {
                throw new AccountNotExistException(login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Account getById(Long id) throws ServiceException {
        try {
            return accountDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private Account getByLogin(String login) throws ServiceException {
        try {
            Account account = accountDao.readByLogin(login);
            if (account != null) {
                return account;
            } else {
                throw new AccountNotExistException(login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
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
                    SubscriberAction subscriberAction = createSubscriberAction(id);
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
    public void changePassword(String oldPassword, String newPassword, Long id) throws ServiceException {
        try {
            transaction.start();
            Account account = getById(id);
            if (account.getPassword().equals(accountDao.passwordToSHA(oldPassword))) {
                account.setPassword(accountDao.passwordToSHA(newPassword));
                accountDao.update(account);
            } else {
                throw new AccountPasswordIncorrectException(oldPassword);
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
    public Account resetPassword(String login, String phoneNumber) throws ServiceException {
        try {
            transaction.start();
            Account account = getByLogin(login);
            Subscriber subscriber = subscriberDao.readByPhoneNumber(phoneNumber);
            if (subscriber == null) {
                throw new SubscriberNotExistException(phoneNumber);
            }
            if (subscriber.getAccountId().equals(account.getId())) {
                account.setPassword(accountDao.passwordToSHA("12345"));
                accountDao.update(account);
                transaction.commit();
            } else {
                 throw new AccountNotExistException(login);
            }
            return account;
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
    public void changeSubscribersPersonalData(Account account, Subscriber subscriber, String login,
                                              String phoneNumber) throws ServiceException {
        try {
            transaction.start();
            if (!account.getLogin().equals(login)) {
                if (accountDao.readByLogin(login) != null) {
                    throw new AccountLoginNotUniqueException(login);
                }
            }
            if (!subscriber.getPhoneNumber().equals(phoneNumber)) {
                if (subscriberDao.readByPhoneNumber(phoneNumber) != null) {
                    throw new SubscriberPhoneNotUniqueException(phoneNumber);
                }
            }
            account.setLogin(login);
            accountDao.update(account);
            subscriber.setPhoneNumber(phoneNumber);
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

        private SubscriberAction createSubscriberAction (Long subscriberId){
            SubscriberAction subscriberAction = new SubscriberAction();
            subscriberAction.setAction(Action.REGISTRATION);
            subscriberAction.setSubscriberId(subscriberId);
            subscriberAction.setDate(LocalDateTime.now());
            subscriberAction.setSum(0);
            return subscriberAction;
        }
    }
