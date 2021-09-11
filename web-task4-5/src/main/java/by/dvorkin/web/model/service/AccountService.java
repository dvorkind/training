package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

public interface AccountService {
    Account login(String login, String password) throws ServiceException;

    Account getById(Long id) throws ServiceException;

    Account getByLogin(String login) throws ServiceException;

    void create(Account account, Subscriber subscriber) throws ServiceException;

    void update(Account account) throws ServiceException;

    void changePassword(String oldPassword, String newPassword, Long id) throws ServiceException;

    Account resetPassword(Account account) throws ServiceException;
}
