package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.TariffLastException;
import by.dvorkin.web.model.service.exceptions.TariffNameNotUniqueException;

import java.util.List;

public class TariffServiceImpl implements TariffService {
    private TariffDao tariffDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Override
    public List<Tariff> getAll() throws ServiceException {
        try {
            return tariffDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Tariff getById(Long id) throws ServiceException {
        try {
            return tariffDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void save(Tariff tariff) throws ServiceException {
        try {
            transaction.start();
            Tariff existingTariff = tariffDao.readByName(tariff.getName());

            //create new tariff
            if (existingTariff == null && tariff.getId() == null) {
                tariff.setId(tariffDao.create(tariff));
            }

            //tariff with this name already exists
            if (existingTariff != null && tariff.getId() == null) {
                throw new TariffNameNotUniqueException(tariff.getName());
            }

            //update existing tariff
            if (existingTariff == null && tariff.getId() != null) {
                tariffDao.update(tariff);
            }

            //update tariff with the same name or not
            if (existingTariff != null && tariff.getId() != null) {
                if (existingTariff.getId().equals(tariff.getId())) {
                    tariffDao.update(tariff);
                } else {
                    throw new TariffNameNotUniqueException(tariff.getName());
                }
            }
            transaction.commit();
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e.getMessage());
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }

    @Override
    public void safetyDelete(Long id, Long newTariffId) throws ServiceException {
        try {
            transaction.start();
            if (!tariffDao.isLastTariff()) {
                if (newTariffId !=0 ) {
                    switchTariffs(id, newTariffId);
                }
                tariffDao.delete(id);
                transaction.commit();
            } else {
                throw new TariffLastException(id.toString());
            }
        } catch (DaoException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw new ServiceException(e.getMessage());
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }

    @Override
    public int getCountUsingTariff(Long id) throws ServiceException {
        try {
            return tariffDao.readCountUsingTariff(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void switchTariffs(Long sourceId, Long destinationId) throws ServiceException {
        try {
              tariffDao.switchTariffs(sourceId, destinationId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
