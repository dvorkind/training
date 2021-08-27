package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Service;

import java.util.List;

public interface ServiceDao extends Dao<Service> {
    List<Service> readAll() throws DaoException;

    Service readByName(String serviceName) throws DaoException;
}
