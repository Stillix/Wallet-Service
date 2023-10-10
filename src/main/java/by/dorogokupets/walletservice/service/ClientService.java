package by.dorogokupets.walletservice.service;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;


public interface ClientService {

   boolean register(String login);

   Client findClientByLogin(String login);

   boolean authenticate();

   BigDecimal getBalance(Client client);

   boolean debit(Client client, BigDecimal amount);

   boolean credit(Client client, BigDecimal amount);


   List<Transaction> getClientTransactionHistory(Client client);
}
