package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface TariffService {
    List<Tariff> getAll() throws ServiceException;

    String getName(Long id) throws ServiceException;

    void create(Tariff tariff) throws ServiceException;
}
