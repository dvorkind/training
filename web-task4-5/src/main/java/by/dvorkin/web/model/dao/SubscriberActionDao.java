package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.SubscriberAction;

import java.util.Date;

public interface SubscriberActionDao extends Dao<SubscriberAction> {
    Date readLastChangeTariff(Long subscriberId) throws DaoException;
}
