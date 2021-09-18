package by.dvorkin.web.model.dao;

import by.dvorkin.web.model.entity.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {
    /**
     * Method gets all the tariffs
     *
     * @return list of tariffs in {@code List<Tariff>}
     * @throws DaoException in case of exception
     */
    List<Tariff> readAll() throws DaoException;

    /**
     * Method gets Tariff by name
     *
     * @param tariffName parameter value
     * @return <code>Tariff</code> object or null
     * @throws DaoException in case of exception
     */
    Tariff readByName(String tariffName) throws DaoException;

    /**
     * Method checks if the last tariff is in storage
     *
     * @return <code>true</code> if the tariff is the only one left
     * @throws DaoException in case of exception
     */
    boolean isLastTariff() throws DaoException;

    /**
     * Method gets the number of subscribers using the specified tariff
     *
     * @param id tariff id
     * @return number of subscribers using the tariff in <code>int</code>
     * @throws DaoException in case of exception
     */
    int readCountUsingTariff(Long id) throws DaoException;

    /**
     * Method replaces the original tariff of the subscriber with the specified tariff
     *
     * @param sourceId      original tariff id
     * @param destinationId destination tariff id
     * @throws DaoException in case of exception
     */
    void switchTariffs(Long sourceId, Long destinationId) throws DaoException;
}
