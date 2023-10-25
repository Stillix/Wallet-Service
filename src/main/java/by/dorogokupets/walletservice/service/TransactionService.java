package by.dorogokupets.walletservice.service;


import domain.entity.Client;
import domain.entity.Transaction;
import by.dorogokupets.walletservice.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * An interface representing the service for managing financial transactions.
 */
public interface TransactionService {

    /**
     * Perform a debit transaction, withdrawing a specified amount from a client's account.
     *
     * @param client        The client for whom the transaction is performed.
     * @param amount        The amount to be debited.
     * @param transactionId The unique identifier for the transaction.
     * @return true if the transaction is successful, false otherwise.
     */
    boolean debit(Client client, BigDecimal amount) throws ServiceException;

    /**
     * Perform a credit transaction, depositing a specified amount into a client's account.
     *
     * @param client        The client for whom the transaction is performed.
     * @param amount        The amount to be credited.
     * @param transactionId The unique identifier for the transaction.
     * @return true if the transaction is successful, false otherwise.
     */
    boolean credit(Client client, BigDecimal amount) throws ServiceException;

    /**
     * Get the transaction history for a specific client.
     *
     * @param client The client for whom to retrieve the transaction history.
     * @return A list of transactions associated with the client.
     */
    List<Transaction> getClientTransactionHistory(Client client) throws ServiceException;
}