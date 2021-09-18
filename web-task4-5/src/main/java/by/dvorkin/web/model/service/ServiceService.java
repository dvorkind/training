package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.ServiceNameNotUniqueException;

import java.util.List;

public interface ServiceService {
    /**
     * Method gets all the services
     *
     * @return list of services in {@code List<Service>}
     * @throws ServiceException in case of exception
     */
    List<Service> getAll() throws ServiceException;

    /**
     * Method gets all the subscriber's services
     *
     * @param subscriberId subscriber id
     * @return list of subscriber's services in {@code List<Service>}
     * @throws ServiceException in case of exception
     */
    List<Service> getSubscribersService(Long subscriberId) throws ServiceException;

    /**
     * Method gets Service by id
     *
     * @param id parameter value
     * @return <code>Service</code> object or null
     * @throws ServiceException in case of exception
     */
    Service getById(Long id) throws ServiceException;

    /**
     * Method writes or updates service data
     *
     * @param service Service class object
     * @throws ServiceNameNotUniqueException if a service with the same name already exists
     * @throws ServiceException              in case of exception
     */
    void save(Service service) throws ServiceException;

    /**
     * Method deletes service by id
     *
     * @param id parameter value
     * @throws ServiceException in case of exception
     */
    void delete(Long id) throws ServiceException;
}
