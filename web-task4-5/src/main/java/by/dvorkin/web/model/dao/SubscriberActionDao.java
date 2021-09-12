package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.SubscriberAction;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberActionDao extends Dao<SubscriberAction> {
    LocalDateTime readLastChangeTariff(Long subscriberId) throws DaoException;

    LocalDateTime readSubscriberRegistrationDate(Long subscriberId) throws DaoException;

    List<SubscriberAction> readBetweenDates(Long subscriberId, LocalDateTime dateBefore, LocalDateTime dateAfter) throws DaoException;
}
