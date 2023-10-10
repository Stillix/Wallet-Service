package by.dorogokupets.walletservice.repository;


import by.dorogokupets.walletservice.entity.Transaction;

import java.util.List;


public interface TransactionRepository {

    Transaction add(Transaction transaction);

    List<Transaction> findClientTransactionHistoryByLogin(String login);


}
