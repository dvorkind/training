package by.dvorkin.web.model.service;

import by.dvorkin.web.model.service.exceptions.TransactionException;

public interface Transaction {
    /**
     * Method disables transaction autocommit
     *
     * @throws TransactionException in case of exception
     */
    void start() throws TransactionException;

    /**
     * Method commits transactions and enables autocommit
     *
     * @throws TransactionException in case of exception
     */
    void commit() throws TransactionException;

    /**
     * Method cancels transactions and enables autocommit
     *
     * @throws TransactionException in case of exception
     */
    void rollback() throws TransactionException;
}
