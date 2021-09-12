package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface SubscriberService {
    Subscriber getByAccountId(Long id) throws ServiceException;

    List<Subscriber> getAll() throws ServiceException;

    List<Subscriber> getNewSubscribers() throws ServiceException;

    List<Subscriber> getDebtors() throws ServiceException;

    void update(Subscriber subscriber) throws ServiceException;

    void subtractBalance(Subscriber subscriber, int sum, Action action) throws ServiceException;

    void addingBalance(Subscriber subscriber, int sum) throws ServiceException;

    Subscriber getById(Long id) throws ServiceException;

    Subscriber getByPhoneNumber(String phoneNumber) throws ServiceException;

    void switchOnService(Subscriber subscriber, Long serviceId) throws ServiceException;

    void switchOffService(Long subscriberId, Long serviceId) throws ServiceException;

    void changeTariff(Subscriber subscriber, Long tariffId) throws ServiceException;

    void setBlock(Long id) throws ServiceException;

    void activate(Long id) throws ServiceException;

    Boolean invoiceBill(Long id) throws ServiceException;
}
