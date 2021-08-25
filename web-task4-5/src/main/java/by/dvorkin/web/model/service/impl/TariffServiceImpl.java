package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
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
            throw new ServiceException(e);
        }
    }

    @Override
    public String getName(Long id) throws ServiceException {
        try {
            return tariffDao.read(id).getName();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void create(Tariff tariff) throws ServiceException {
        try {
            transaction.start();
            if (tariffDao.readByName(tariff.getName()) == null) {
                tariff.setId(tariffDao.create(tariff));
            } else {
                throw new TariffNameNotUniqueException(tariff.getName());
            }
            transaction.commit();
        } catch (DaoException e) {
            e.printStackTrace();
            transaction.rollback();
        } catch (ServiceException e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (ServiceException ignored) {
            }
            throw e;
        }
    }
}
