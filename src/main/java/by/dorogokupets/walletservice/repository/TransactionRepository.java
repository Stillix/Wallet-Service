package by.dorogokupets.walletservice.repository;


import by.dorogokupets.walletservice.entity.Transaction;

import java.util.List;

/**
 * An interface representing a repository for managing transaction data.
 */

public interface TransactionRepository {
    /**
     * Add transaction to repository
     *
     * @param transaction
     * @return Transaction
     */
    Transaction add(Transaction transaction);

    /**
     * Find client transaction history by login
     *
     * @param login
     * @return List<Transaction>
     */
    List<Transaction> findClientTransactionHistoryByLogin(String login);


}
