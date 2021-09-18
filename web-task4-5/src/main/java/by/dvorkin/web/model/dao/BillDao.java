package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends Dao<Bill> {
    /**
     * Method gets date of the last bill
     *
     * @param subscriberId subscriber id
     * @return date of the last bill in <code>LocalDateTime</code> or null
     * @throws DaoException in case of exception
     */
    LocalDateTime readLastBill(Long subscriberId) throws DaoException;

    /**
     * Method gets all the subscriber's bills
     *
     * @param subscriberId subscriber id
     * @return list of subscriber's bills in {@code List<Bill>}
     * @throws DaoException in case of exception
     */
    List<Bill> readAllSubscribersBill(Long subscriberId) throws DaoException;

    /**
     * Method gets all unpaid subscriber's bills
     *
     * @param subscriberId subscriber's id
     * @return list of unpaid subscriber's bills in {@code List<Bill>}
     * @throws DaoException in case of exception
     */
    List<Bill> readAllUnpaidSubscribersBill(Long subscriberId) throws DaoException;

    /**
     * Method gets all unpaid bills of all subscribers
     *
     * @return list of all unpaid bills in {@code List<Bill>}
     * @throws DaoException in case of exception
     */
    List<Bill> readAllUnpaid() throws DaoException;
}
