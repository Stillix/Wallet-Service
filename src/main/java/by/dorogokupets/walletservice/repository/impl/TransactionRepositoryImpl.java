package by.dorogokupets.walletservice.repository.impl;

import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.repository.TransactionRepository;

import java.util.*;

/**
 * Implementation of the TransactionRepository interface, representing a repository of financial transactions.
 */
public class TransactionRepositoryImpl implements TransactionRepository {
    private static final Map<UUID, Transaction> transactions = new HashMap<>();

    @Override
    public Transaction add(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findClientTransactionHistoryByLogin(String login) {
        List<Transaction> clientTransactions = new ArrayList<>();
        for (Transaction transaction : transactions.values()) {
            if (transaction.getClient().getLogin().equals(login)) {
                clientTransactions.add(transaction);
            }
        }
        return clientTransactions;
    }
}
