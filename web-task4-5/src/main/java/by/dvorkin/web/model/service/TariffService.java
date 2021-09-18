package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.TariffLastException;
import by.dvorkin.web.model.service.exceptions.TariffNameNotUniqueException;

import java.util.List;

public interface TariffService {
    /**
     * Method gets all the tariffs
     *
     * @return list of tariffs in {@code List<Tariff>}
     * @throws ServiceException in case of exception
     */
    List<Tariff> getAll() throws ServiceException;

    /**
     * Method gets an Tariff by id
     *
     * @param id parameter value
     * @return <code>Tariff</code> object
     * @throws ServiceException in case of exception
     */
    Tariff getById(Long id) throws ServiceException;

    /**
     * Method writes or updates tariff data
     *
     * @param tariff Tariff class object
     * @throws TariffNameNotUniqueException if a tariff with the same name already exists
     * @throws ServiceException             in case of exception
     */
    void save(Tariff tariff) throws ServiceException;

    /**
     * Method deletes the tariff specified by id and sets a different tariff for all subscribers
     *
     * @param id          parameter value
     * @param newTariffId parameter value
     * @throws TariffLastException if the tariff to be removed is the last one
     * @throws ServiceException    in case of exception
     */
    void safetyDelete(Long id, Long newTariffId) throws ServiceException;

    /**
     * Method gets the number of subscribers by the specified tariff
     *
     * @param id tariff id
     * @return number of subscribers using the tariff in <code>int</code>
     * @throws ServiceException in case of exception
     */
    int getCountUsingTariff(Long id) throws ServiceException;
}
