package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Subscriber;

import java.util.List;

public interface SubscriberDao extends Dao<Subscriber> {
    /**
     * Method gets all the subscribers
     *
     * @return list of subscribers in {@code List<Subscriber>}
     * @throws DaoException in case of exception
     */
    List<Subscriber> readAll() throws DaoException;

    /**
     * Method gets all new subscribers
     *
     * @return list of new subscribers in {@code List<Subscriber>}
     * @throws DaoException in case of exception
     */
    List<Subscriber> readNewSubscribers() throws DaoException;

    /**
     * Method gets all subscribers with negative balance
     *
     * @return list of debtors in {@code List<Subscriber>}
     * @throws DaoException in case of exception
     */
    List<Subscriber> readDebtors() throws DaoException;

    /**
     * Method gets Subscriber by phone number
     *
     * @param phoneNumber parameter value
     * @return <code>Subscriber</code> object or null
     * @throws DaoException in case of exception
     */
    Subscriber readByPhoneNumber(String phoneNumber) throws DaoException;

    /**
     * Method gets Subscriber by account id
     *
     * @param id parameter value
     * @return <code>Subscriber</code> object or null
     * @throws DaoException in case of exception
     */
    Subscriber readByAccountId(Long id) throws DaoException;

}
