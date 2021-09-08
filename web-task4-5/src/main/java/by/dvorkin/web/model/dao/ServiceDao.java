package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Service;

import java.util.List;

public interface ServiceDao extends Dao<Service> {
    List<Service> readAll() throws DaoException;

    List<Service> readSubscribersService(Long subscriber_id) throws DaoException;

    Service readByName(String serviceName) throws DaoException;

    void switchOn (Long subscriber_id, Long service_id) throws DaoException;

    void switchOff (Long subscriber_id, Long service_id) throws DaoException;
}
