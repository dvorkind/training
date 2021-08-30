package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Date;

public interface SubscriberActionService {
    void create(SubscriberAction subscriberAction) throws ServiceException;

    Date getLastChangeTariff(Long subscriberId) throws ServiceException;
}
