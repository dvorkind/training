package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionService {
    void create(SubscriberAction subscriberAction) throws ServiceException;

    List<SubscriberAction> getActionsBetweenDates(Long subscriberId, LocalDateTime dateBefore,
                                                  LocalDateTime dateAfter) throws ServiceException;

    LocalDateTime getLastChangeTariff(Long subscriberId) throws ServiceException;
}
