package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionService {
    List<SubscriberAction> getActionsBetweenDates(Long subscriberId, LocalDateTime dateBefore,
                                                  LocalDateTime dateAfter) throws ServiceException;
}
