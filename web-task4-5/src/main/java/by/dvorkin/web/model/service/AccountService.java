package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.User;
import by.dvorkin.web.model.service.exceptions.ServiceException;

public interface AccountService {
    Account login(String login, String password) throws ServiceException;

    void create(Account account, User user) throws ServiceException;

    void changePassword(String oldPassword, String newPassword, Account account) throws ServiceException;
}
