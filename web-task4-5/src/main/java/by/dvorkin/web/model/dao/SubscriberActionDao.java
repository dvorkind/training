package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.SubscriberAction;

import java.util.Date;
import java.util.List;

public interface SubscriberActionDao extends Dao<SubscriberAction> {
    Date readLastChangeTariff(Long subscriberId) throws DaoException;

    List<SubscriberAction> readBetweenDates(Long subscriberId, Date dateBefore, Date dateAfter) throws DaoException;
}
