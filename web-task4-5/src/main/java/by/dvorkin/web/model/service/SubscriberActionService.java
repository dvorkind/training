package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberActionNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionService {
    /**
     * Method gets all the subscriber's actions during the selected period
     *
     * @param subscriberId subscriber id
     * @param dateBefore   start date
     * @param dateAfter    end date
     * @return list of subscriber actions in {@code List<SubscriberAction>}
     * @throws SubscriberActionNotFoundException if no subscriber's actions were found
     * @throws ServiceException                  in case of exception
     */
    List<SubscriberAction> getActionsBetweenDates(Long subscriberId, LocalDateTime dateBefore,
                                                  LocalDateTime dateAfter) throws ServiceException;
}
