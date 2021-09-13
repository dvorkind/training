package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.SubscriberAction;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionDao extends Dao<SubscriberAction> {
    /**
     * Method gets last date of changing tariff
     * @param subscriberId the parameter value
     * @return last date of changing tariff in <code>LocalDateTime</code>
     * @throws DaoException in case if exception occurred
     */
    LocalDateTime readLastChangeTariff(Long subscriberId) throws DaoException;

    LocalDateTime readSubscriberRegistrationDate(Long subscriberId) throws DaoException;

    List<SubscriberAction> readBetweenDates(Long subscriberId, LocalDateTime dateBefore, LocalDateTime dateAfter) throws DaoException;
}
