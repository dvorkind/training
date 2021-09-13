package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends Dao<Bill> {
    LocalDateTime readLastBill(Long subscriberId) throws DaoException;

    List<Bill> readAllSubscribersBill(Long subscriberId) throws DaoException;

    List<Bill> readAllUnpaidSubscribersBill(Long subscriberId) throws DaoException;

    List<Bill> readAllUnpaid() throws DaoException;


}
