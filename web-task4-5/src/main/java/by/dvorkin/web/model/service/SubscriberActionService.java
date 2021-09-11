package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

public interface SubscriberActionService {
    void create(SubscriberAction subscriberAction) throws ServiceException;

    List<SubscriberAction> getActionsBetweenDates(Long subscriberId, Date dateBefore, Date dateAfter) throws ServiceException;

    Date getLastChangeTariff(Long subscriberId) throws ServiceException;
}
