package by.dvorkin.web.model.service;

import by.dvorkin.web.model.entity.User;
import by.dvorkin.web.model.service.exceptions.ServiceException;

import java.util.Map;

public interface UserService {
    User findByAccountId(Long id) throws ServiceException;

    Map<String, User> getAll() throws ServiceException;

    Map<String, User> getNewUsers() throws ServiceException;

    void activate(int id) throws ServiceException;
}
