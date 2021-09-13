package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface BillService {
    void delete(Long id) throws ServiceException;

    List<Bill> getAllSubscribersBill(Long subscriberId) throws ServiceException;

    List<Bill> getAllUnpaid() throws ServiceException;

    void payBill(Long id, Subscriber subscriber) throws ServiceException;
}
