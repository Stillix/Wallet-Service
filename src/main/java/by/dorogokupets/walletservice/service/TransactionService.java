package by.dorogokupets.walletservice.service;


import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    boolean debit(Client client, BigDecimal amount, UUID transactionId);

    boolean credit(Client client, BigDecimal amount, UUID transactionId);

    List<Transaction> getClientTransactionHistory(Client client);


}
