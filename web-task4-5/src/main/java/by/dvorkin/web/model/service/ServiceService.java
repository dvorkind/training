package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface ServiceService {
    List<Service> getAll() throws ServiceException;

    List<Service> getSubscribersService(Long subscriber_id) throws ServiceException;

    Service readById(Long id) throws ServiceException;

    void save(Service service) throws ServiceException;

    void delete(Long id) throws ServiceException;

    void switchOn(Long subscriber_id, Long service_id) throws ServiceException;

    void switchOff(Long subscriber_id, Long service_id) throws ServiceException;
}
