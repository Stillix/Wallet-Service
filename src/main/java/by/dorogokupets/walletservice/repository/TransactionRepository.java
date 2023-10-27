package by.dorogokupets.walletservice.repository;


import by.dorogokupets.walletservice.domain.entity.Transaction;
import by.dorogokupets.walletservice.exception.RepositoryException;

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
    Transaction add(Transaction transaction) throws RepositoryException;

    /**
     * Find client transaction history by login
     *
     * @param clientId
     * @return List<Transaction>
     */
    List<Transaction> findClientTransactionHistoryByClientId(int clientId) throws RepositoryException;


}
