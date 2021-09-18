package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Service;

import java.util.List;

public interface ServiceDao extends Dao<Service> {
    /**
     * Method gets all the services
     *
     * @return list of services in {@code List<Service>}
     * @throws DaoException in case of exception
     */
    List<Service> readAll() throws DaoException;

    /**
     * Method gets all the subscriber's services
     *
     * @param subscriberId subscriber id
     * @return list of subscriber's services in {@code List<Service>}
     * @throws DaoException in case of exception
     */
    List<Service> readSubscribersService(Long subscriberId) throws DaoException;

    /**
     * Method gets Service by name
     *
     * @param serviceName parameter value
     * @return an <code>Service</code> object or null
     * @throws DaoException in case of exception
     */
    Service readByName(String serviceName) throws DaoException;

    /**
     * Method adds the service to the subscriber
     *
     * @param subscriberId subscriber id
     * @param serviceId    service id
     * @throws DaoException in case of exception
     */
    void switchOn(Long subscriberId, Long serviceId) throws DaoException;

    /**
     * Method deletes the service from the subscriber
     *
     * @param subscriberId subscriber id
     * @param serviceId    service id
     * @throws DaoException in case of exception
     */
    void switchOff(Long subscriberId, Long serviceId) throws DaoException;
}
