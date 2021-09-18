package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;

import java.util.List;

public interface BillService {
    /**
     * Method deletes the bill by id
     *
     * @param id parameter value
     * @throws ServiceException in case of exception
     */
    void delete(Long id) throws ServiceException;

    /**
     * Method gets all the subscriber's bills
     *
     * @param subscriberId subscriber id
     * @return list of subscriber's bills in {@code List<Bill>}
     * @throws ServiceException in case of exception
     */
    List<Bill> getAllSubscribersBill(Long subscriberId) throws ServiceException;

    /**
     * Method gets all unpaid bills of all subscribers
     *
     * @return list of all unpaid bills in {@code List<Bill>}
     * @throws ServiceException in case of exception
     */
    List<Bill> getAllUnpaid() throws ServiceException;

    /**
     * Method marks the specified bill as paid and decreases the subscriber's balance by the bill amount
     *
     * @param id         bill id
     * @param subscriber current Subscriber object
     * @throws SubscriberNotEnoughMoneyException if the subscriber does not have enough money on the balance
     * @throws ServiceException                  in case of exception
     */
    void payBill(Long id, Subscriber subscriber) throws ServiceException;
}
