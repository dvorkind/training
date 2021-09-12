package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends Dao<Bill> {
    LocalDateTime readLastBill(Long subscriberId) throws DaoException;

    List<Bill> readAll(Long subscriberId) throws DaoException;

    List<Bill> readAllPaid(Long subscriberId) throws DaoException;

    List<Bill> readAllUnpaid(Long subscriberId) throws DaoException;
}
