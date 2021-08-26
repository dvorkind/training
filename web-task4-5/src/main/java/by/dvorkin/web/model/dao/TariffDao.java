package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {
    List<Tariff> readAll() throws DaoException;

    Tariff readByName(String tariffName) throws DaoException;

    boolean isLastTariff() throws DaoException;

    int getCountUsingTariff(Long id) throws DaoException;

    void switchTariffs(Long sourceId, Long destinationId) throws DaoException;
}
