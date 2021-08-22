package by.dvorkin.web.model.service;

import by.dvorkin.web.model.service.exceptions.TransactionException;

public interface Transaction {
    void start() throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;
}
