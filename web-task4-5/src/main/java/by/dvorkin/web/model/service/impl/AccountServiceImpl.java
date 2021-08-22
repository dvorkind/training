package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.UserDao;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.User;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.AccountPasswordIncorrectException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.UserPhoneNotUniqueException;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private UserDao userDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
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
    public void create(Account account, User user) throws ServiceException {
        try {
            transaction.start();
            if (accountDao.readByLogin(account.getLogin()) == null) {
                if (userDao.readByPhoneNumber(user.getPhoneNumber()) == null) {
                    Long id = accountDao.create(account);
                    account.setId(id);
                    user.setAccountId(id);
                    id = userDao.create(user);
                    user.setId(id);
                } else {
                    throw new UserPhoneNotUniqueException(user.getPhoneNumber());
                }
            } else {
                throw new AccountLoginNotUniqueException(account.getLogin());
            }
            transaction.commit();
        } catch (DaoException e) {
            e.printStackTrace();
            transaction.rollback();
        } catch (ServiceException e) {
            e.printStackTrace();
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
            if (account.getPassword()
                    .equals(accountDao.passwordToSHA(oldPassword))) {
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
}
