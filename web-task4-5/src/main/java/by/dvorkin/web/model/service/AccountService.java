package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.AccountNotExistException;
import by.dvorkin.web.model.service.exceptions.AccountPasswordIncorrectException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotExistException;
import by.dvorkin.web.model.service.exceptions.SubscriberPhoneNotUniqueException;

public interface AccountService {
    /**
     * Method gets Account by login and password
     *
     * @param login    parameter value
     * @param password parameter value
     * @return Account class object
     * @throws AccountNotExistException if the Account with this login does not exist
     * @throws ServiceException         in case of exception
     */
    Account login(String login, String password) throws ServiceException;

    /**
     * Method gets Account by id
     *
     * @param id parameter value
     * @return Account class object
     * @throws ServiceException in case of exception
     */
    Account getById(Long id) throws ServiceException;

    /**
     * Method writes the Account and Subscriber objects
     *
     * @param account    Account class object
     * @param subscriber Subscriber class object
     * @throws SubscriberPhoneNotUniqueException if a Subscriber with the same phone number already exists
     * @throws AccountLoginNotUniqueException    if an Account with the same login already exists
     * @throws ServiceException                  in case of exception
     */
    void create(Account account, Subscriber subscriber) throws ServiceException;

    /**
     * Method changes the old password to a new one for the Account specified by id
     *
     * @param oldPassword parameter value
     * @param newPassword parameter value
     * @param id          account id
     * @throws AccountPasswordIncorrectException if the old password is incorrect
     * @throws ServiceException                  in case of exception
     */
    void changePassword(String oldPassword, String newPassword, Long id) throws ServiceException;

    /**
     * Method resets the password to "12345" for the account with the specified login and phone number
     *
     * @param login       parameter value
     * @param phoneNumber parameter value
     * @return Account class object with new password
     * @throws SubscriberNotExistException if a Subscriber with this phone number does not exist
     * @throws AccountNotExistException    if an Account with this login does not exist
     * @throws ServiceException            in case of exception
     */
    Account resetPassword(String login, String phoneNumber) throws ServiceException;

    /**
     * Method changes the subscriber's personal data
     *
     * @param account     current Account object
     * @param subscriber  current Subscriber object
     * @param login       new account login
     * @param phoneNumber new subscriber's phone number
     * @throws SubscriberPhoneNotUniqueException if another Subscriber with the same phone number already exists
     * @throws AccountLoginNotUniqueException    if another Account with the same login already exists
     * @throws ServiceException                  in case of exception
     */
    void changeSubscribersPersonalData(Account account, Subscriber subscriber, String login, String phoneNumber) throws ServiceException;
}
