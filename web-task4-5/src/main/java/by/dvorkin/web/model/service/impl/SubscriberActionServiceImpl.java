package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberActionNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriberActionServiceImpl implements SubscriberActionService {
    private SubscriberActionDao subscriberActionDao;

    public void setSubscriberActionDao(SubscriberActionDao subscriberActionDao) {
        this.subscriberActionDao = subscriberActionDao;
    }

    @Override
    public List<SubscriberAction> getActionsBetweenDates(Long subscriberId, LocalDateTime dateBefore,
                                                         LocalDateTime dateAfter) throws ServiceException {
        try {
            List<SubscriberAction> actions;
            if (dateAfter.isAfter(dateBefore)) {
                actions = subscriberActionDao.readBetweenDates(subscriberId, dateBefore, dateAfter);
            } else {
                actions = subscriberActionDao.readBetweenDates(subscriberId, dateAfter, dateBefore);
            }
            if (actions.size() == 0) {
                throw new SubscriberActionNotFoundException(String.valueOf(subscriberId));
            }
            return actions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
