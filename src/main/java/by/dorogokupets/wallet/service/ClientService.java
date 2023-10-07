package by.dorogokupets.wallet.service;

import by.dorogokupets.wallet.entity.Client;
import by.dorogokupets.wallet.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;


public interface ClientService {

   boolean registerClient(String login, String password, String firstName, String lastName);

   Client getClientByLogin(String login);

   boolean authenticate(String login, String password);

   BigDecimal getBalance(Client client);

   boolean debit(Client client, BigDecimal amount);

   boolean credit(Client client, BigDecimal amount);

   List<Transaction> getClientTransactionHistory(Client client);

}
