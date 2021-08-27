package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Subscriber;

import java.util.List;

public interface SubscriberDao extends Dao<Subscriber> {
    List<Subscriber> readAll() throws DaoException;

    List<Subscriber> readNewSubscribers() throws DaoException;

    Subscriber readByPhoneNumber(String phoneNumber) throws DaoException;

    Subscriber readByAccountId(Long id) throws DaoException;

    void activate(int id) throws DaoException;

}
