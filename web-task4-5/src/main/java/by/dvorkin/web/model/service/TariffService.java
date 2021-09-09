package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.List;

public interface TariffService {
    List<Tariff> getAll() throws ServiceException;

    Tariff getById(Long id) throws ServiceException;

    void save(Tariff tariff) throws ServiceException;

    void safetyDelete(Long id) throws ServiceException;

    int getCountUsingTariff(Long id) throws ServiceException;

    void switchTariffs(Long sourceId, Long destinationId) throws ServiceException;
}
