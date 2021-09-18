package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.BillTooEarlyException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberCanNotChangeTariffException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;

import java.util.List;

public interface SubscriberService {
    /**
     * Method gets Subscriber by account id
     *
     * @param id parameter value
     * @return <code>Subscriber</code> object
     * @throws ServiceException in case of exception
     */
    Subscriber getByAccountId(Long id) throws ServiceException;

    /**
     * Method gets all the subscribers
     *
     * @return list of subscribers in {@code List<Subscriber>}
     * @throws ServiceException in case of exception
     */
    List<Subscriber> getAll() throws ServiceException;

    /**
     * Method gets all new subscribers
     *
     * @return list of new subscribers in {@code List<Subscriber>}
     * @throws ServiceException in case of exception
     */
    List<Subscriber> getNewSubscribers() throws ServiceException;

    /**
     * Method gets all subscribers who have a negative balance
     *
     * @return list of debtors in {@code List<Subscriber>}
     * @throws ServiceException in case of exception
     */
    List<Subscriber> getDebtors() throws ServiceException;

    /**
     * Method updates the passed object
     *
     * @param subscriber current subscriber
     * @throws ServiceException in case of exception
     */
    void update(Subscriber subscriber) throws ServiceException;

    /**
     * Method reduces the subscriber's balance by a specified sum and saves it to subscriber's action history
     *
     * @param subscriber current subscriber
     * @param sum        parameter value
     * @param action     object of Action class
     * @throws SubscriberNotEnoughMoneyException if the subscriber does not have enough money on the balance
     * @throws ServiceException                  in case of exception
     */
    void subtractBalance(Subscriber subscriber, int sum, Action action) throws ServiceException;

    /**
     * Method increases the subscriber's balance by a specified sum and saves it to subscriber's action history
     *
     * @param subscriber current subscriber
     * @param sum        parameter value
     * @throws ServiceException in case of exception
     */
    void addingBalance(Subscriber subscriber, int sum) throws ServiceException;

    /**
     * Method gets Subscriber by account id
     *
     * @param id parameter value
     * @return <code>Subscriber</code> object
     * @throws ServiceException in case of exception
     */
    Subscriber getById(Long id) throws ServiceException;

    /**
     * Method adds the service to the subscriber and saves it to subscriber's action history
     *
     * @param subscriberId subscriber id
     * @param serviceId    service id
     * @throws ServiceException in case of exception
     */
    void switchOnService(Long subscriberId, Long serviceId) throws ServiceException;

    /**
     * Method deletes the service from the subscriber and saves it to subscriber's action history
     *
     * @param subscriberId subscriber id
     * @param serviceId    service id
     * @throws ServiceException in case of exception
     */
    void switchOffService(Long subscriberId, Long serviceId) throws ServiceException;

    /**
     * Method changes the subscriber's tariff and saves it to subscriber's action history
     *
     * @param subscriber current subscriber
     * @param tariffId   tariff id
     * @throws SubscriberCanNotChangeTariffException if the time since the last tariff change or subscriber's
     * registration is less than 24h
     * @throws ServiceException                      in case of exception
     */
    void changeTariff(Subscriber subscriber, Long tariffId) throws ServiceException;

    /**
     * Method changes the subscriber's tariff and saves it to subscriber's action history without any checking
     *
     * @param subscriber current subscriber
     * @param tariffId   tariff id
     * @throws ServiceException in case of exception
     */
    void changeTariffByAdmin(Subscriber subscriber, Long tariffId) throws ServiceException;

    /**
     * Method blocks subscriber by id
     *
     * @param id parameter value
     * @throws ServiceException in case of exception
     */
    void setBlock(Long id) throws ServiceException;

    /**
     * Method activates subscriber by id
     *
     * @param id parameter value
     * @throws ServiceException in case of exception
     */
    void activate(Long id) throws ServiceException;

    /**
     * Method issues a bill to the subscriber by id
     *
     * @param id parameter value
     * @throws BillTooEarlyException if the time since the last tariff change or subscriber's registration is less
     * than 24h
     * @throws ServiceException      in case of exception
     */
    void issueBill(Long id) throws ServiceException;

    /**
     * Method issues a bill to all subscribers
     *
     * @return number of successfully issued bills in <code>int</code>
     * @throws ServiceException in case of exception
     */
    int issueBillToAll() throws ServiceException;
}
