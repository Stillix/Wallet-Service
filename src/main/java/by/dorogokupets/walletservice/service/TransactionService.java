package by.dorogokupets.walletservice.service;


import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    boolean debit(Client client, BigDecimal amount, UUID transactionId);

    /**
     * Perform a credit transaction, depositing a specified amount into a client's account.
     *
     * @param client        The client for whom the transaction is performed.
     * @param amount        The amount to be credited.
     * @param transactionId The unique identifier for the transaction.
     * @return true if the transaction is successful, false otherwise.
     */
    boolean credit(Client client, BigDecimal amount, UUID transactionId);

    /**
     * Get the transaction history for a specific client.
     *
     * @param client The client for whom to retrieve the transaction history.
     * @return A list of transactions associated with the client.
     */
    List<Transaction> getClientTransactionHistory(Client client);
}