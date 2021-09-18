package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.SubscriberAction;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionDao extends Dao<SubscriberAction> {
    /**
     * Method gets date of last tariff change
     *
     * @param subscriberId subscriber id
     * @return date of last tariff change in <code>LocalDateTime</code> or null
     * @throws DaoException in case of exception
     */
    LocalDateTime readLastChangeTariff(Long subscriberId) throws DaoException;

    /**
     * Method gets the subscriber's registration date
     *
     * @param subscriberId subscriber id
     * @return registration date in <code>LocalDateTime</code>
     * @throws DaoException in case of exception
     */
    LocalDateTime readRegistrationDate(Long subscriberId) throws DaoException;

    /**
     * Method gets all the subscriber's actions during the selected period
     *
     * @param subscriberId subscriber id
     * @param dateBefore   start date
     * @param dateAfter    end date
     * @return list of subscriber's actions in {@code List<SubscriberAction>}
     * @throws DaoException in case of exception
     */
    List<SubscriberAction> readBetweenDates(Long subscriberId, LocalDateTime dateBefore, LocalDateTime dateAfter) throws DaoException;
}
