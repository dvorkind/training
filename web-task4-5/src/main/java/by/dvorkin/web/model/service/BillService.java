package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

public interface BillService {
    void create(Bill bill) throws ServiceException;

    void delete(Long id) throws ServiceException;

    Bill getById(Long id) throws ServiceException;

    Date getLastBill(Long subscriberId) throws ServiceException;

    List<Bill> getAll(Long subscriberId) throws ServiceException;

    List<Bill> getAllPaid(Long subscriberId) throws ServiceException;

    List<Bill> getAllUnpaid(Long subscriberId) throws ServiceException;

    int payBill(Long id, Subscriber subscriber) throws ServiceException;
}
