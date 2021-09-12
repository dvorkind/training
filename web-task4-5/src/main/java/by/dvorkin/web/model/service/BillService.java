package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface BillService {
    void create(Bill bill) throws ServiceException;

    void delete(Long id) throws ServiceException;

    Bill getById(Long id) throws ServiceException;

    LocalDateTime getLastBill(Long subscriberId) throws ServiceException;

    List<Bill> getAll(Long subscriberId) throws ServiceException;

    List<Bill> getAllPaid(Long subscriberId) throws ServiceException;

    List<Bill> getAllUnpaid(Long subscriberId) throws ServiceException;

    void payBill(Long id, Subscriber subscriber) throws ServiceException;
}
