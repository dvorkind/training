package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface SubscriberService {
    Subscriber getByAccountId(Long id) throws ServiceException;

    List<Subscriber> getAll() throws ServiceException;

    List<Subscriber> getNewSubscribers() throws ServiceException;

    List<Subscriber> getDebtors() throws ServiceException;

    void update(Subscriber subscriber) throws ServiceException;

    Subscriber getById(Long id) throws ServiceException;

    Subscriber getByPhoneNumber(String phoneNumber) throws ServiceException;
}
