package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface ServiceService {
    List<Service> getAll() throws ServiceException;

    List<Service> getSubscribersService(Long subscriberId) throws ServiceException;

    Service getById(Long id) throws ServiceException;

    void save(Service service) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
