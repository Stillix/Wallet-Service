package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.service.ClientService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientService {
   private ClientRepository clientRepository;
   private List<Transaction> transactions = new ArrayList<>();

   public ClientServiceImpl(ClientRepository clientRepository) {
      this.clientRepository = clientRepository;
   }

   @Override
   public boolean registerClient(String login, String password, String firstName, String lastName) {
      return clientRepository.registerClient(login, password, firstName, lastName);
   }

   @Override
   public Client getClientByLogin(String login) {
      return clientRepository.getClientByLogin(login);
   }

   @Override
   public boolean authenticate(String login, String password) {
      return clientRepository.authenticate(login, password);
   }

   @Override
   public BigDecimal getBalance(Client client) {
      return client.getBalance();
   }

   @Override
   public boolean debit(Client client, BigDecimal amount) {
      if (client.getBalance().compareTo(amount) >= 0) {
         BigDecimal newBalance = client.getBalance().subtract(amount);
         client.setBalance(newBalance);
         transactions.add(new Transaction(client, TransactionType.DEBIT));
         return true;
      }
      return false;
   }

   @Override
   public boolean credit(Client client, BigDecimal amount) {
      if (amount.compareTo(BigDecimal.ZERO) > 0) {
         BigDecimal newBalance = client.getBalance().add(amount);
         client.setBalance(newBalance);
         transactions.add(new Transaction(client, TransactionType.CREDIT));
         return true;
      }
      return false;
   }

   @Override
   public List<Transaction> getClientTransactionHistory(Client client) {
      List<Transaction> history = new ArrayList<>();
      for (Transaction transaction : transactions) {
         if (transaction.getClient().equals(client)) {
            history.add(transaction);
         }
      }
      return history;
   }
}
