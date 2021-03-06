package by.dvorkin.web.model.service.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.Transaction;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.ServiceNameNotUniqueException;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    private ServiceDao serviceDao;
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public List<Service> getAll() throws ServiceException {
        try {
            return serviceDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Service> getSubscribersService(Long subscriberId) throws ServiceException {
        try {
            return serviceDao.readSubscribersService(subscriberId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Service getById(Long id) throws ServiceException {
        try {
            return serviceDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void save(Service service) throws ServiceException {
        try {
            transaction.start();
            Service existingService = serviceDao.readByName(service.getName());

            //create new service
            if (existingService == null && service.getId() == null) {
                service.setId(serviceDao.create(service));
            }

            //service with this name already exists
            if (existingService != null && service.getId() == null) {
                throw new ServiceNameNotUniqueException(service.getName());
            }

            //update existing service
            if (existingService == null && service.getId() != null) {
                serviceDao.update(service);
            }

            //update service with the same name or not
            if (existingService != null && service.getId() != null) {
                if (existingService.getId().equals(service.getId())) {
                    serviceDao.update(service);
                } else {
                    throw new ServiceNameNotUniqueException(service.getName());
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
    public void delete(Long id) throws ServiceException {
        try {
            transaction.start();
            serviceDao.delete(id);
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
}
