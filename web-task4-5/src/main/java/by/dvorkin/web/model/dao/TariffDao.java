package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {
    List<Tariff> readAll() throws DaoException;

    Tariff readByName(String tariffName) throws DaoException;
    //TODO: добавить проверку невозможности удаления последнего тарифа
}
