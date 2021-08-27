package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

public interface AccountService {
    Account login(String login, String password) throws ServiceException;

    void create(Account account, Subscriber subscriber) throws ServiceException;

    void changePassword(String oldPassword, String newPassword, Account account) throws ServiceException;
}
