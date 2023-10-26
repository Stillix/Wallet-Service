package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.validator.TransactionValidator;
import domain.dto.TransactionDto;
import domain.entity.Client;
import domain.entity.Transaction;
import domain.enums.TransactionType;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TransactionService interface. A service class for handling client operations, such as registration, authentication, and balance retrieval.
 */
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;
  private final ClientRepository clientRepository;

  public TransactionServiceImpl(TransactionRepository transactionRepository, ClientRepository clientRepository) {
    this.transactionRepository = transactionRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean processTransaction(TransactionDto transactionDto) throws ServiceException {

    Optional<Client> clientOptional;
    try {
      clientOptional = clientRepository.findClientById(transactionDto.getClientId());
    } catch (RepositoryException e) {
      throw new ServiceException(e);
    }
    Client client = clientOptional.get();

    if (transactionDto.getType() == TransactionType.DEBIT) {
      return this.debit(client, transactionDto.getAmount());
    } else if (transactionDto.getType() == TransactionType.CREDIT) {
      return this.credit(client, transactionDto.getAmount());
    }
    return false;
  }

  @Override
  public boolean debit(Client client, BigDecimal amount) throws ServiceException {
    if (amount.doubleValue() <= 0) {
      return false;
    }
    if (client.getBalance().compareTo(amount) < 0) {
      System.out.println("Недостаточно средств.");
      return false;
    }
    BigDecimal newBalance = client.getBalance().subtract(amount);
    client.setBalance(newBalance);

    try {
      clientRepository.updateBalance(client.getClientId(), newBalance);
    } catch (RepositoryException e) {
      throw new ServiceException(e.getMessage());
    }

    Timestamp ts = Timestamp.from(ZonedDateTime.now().toInstant());
    Transaction transaction = new Transaction(client, TransactionType.DEBIT, amount, ts);

    try {
      transactionRepository.add(transaction);
    } catch (RepositoryException e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public boolean credit(Client client, BigDecimal amount) throws ServiceException {
    if (amount.doubleValue() > 0) {
      BigDecimal newBalance = client.getBalance().add(amount);
      client.setBalance(newBalance);

      try {
        clientRepository.updateBalance(client.getClientId(), newBalance);
      } catch (RepositoryException e) {
        throw new ServiceException(e.getMessage());
      }

      Timestamp ts = Timestamp.from(ZonedDateTime.now().toInstant());
      Transaction transaction = new Transaction(client, TransactionType.CREDIT, amount, ts);

      try {
        transactionRepository.add(transaction);
      } catch (RepositoryException e) {
        throw new ServiceException(e.getMessage());
      }
      return true;
    }
    return false;
  }


  @Override
  public List<Transaction> getClientTransactionHistory(Client client) throws ServiceException {
    int clientId = client.getClientId();
    List<Transaction> transactions;
    try {
      transactions = transactionRepository.findClientTransactionHistoryByClientId(clientId);
    } catch (RepositoryException e) {
      throw new ServiceException(e.getMessage());
    }
    if (transactions.isEmpty()) {
      System.out.println("У Вас, " + client.getClientFirstName() + ", нет транзакций!");
    } else {
      for (Transaction transaction : transactions) {
        System.out.println(transaction);
      }
    }
    return transactions;
  }
}
