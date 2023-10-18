package by.dorogokupets.walletservice.repository.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;


/**
 * Implementation of the TransactionRepository interface, representing a repository of financial transactions.
 */
public class TransactionRepositoryImpl implements TransactionRepository {
  private static final Properties prop = new Properties();
  private static final String url;
  private static final String username;
  private static final String password;
  private static final String INSERT_TRANSACTION = "INSERT INTO entities.transactions (transaction_id,amount, client_id, type,timestamp) VALUES (?,?, ?, ?,?)";
  private static final String SELECT_TRANSACTIONS_BY_CLIENT_ID = "SELECT * FROM entities.transactions WHERE client_id = ?";
  ClientRepository clientRepository = new ClientRepositoryImpl();

  static {
    try (InputStream input = TransactionRepositoryImpl.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
      prop.load(input);
      url = prop.getProperty("url");
      username = prop.getProperty("username");
      password = prop.getProperty("password");
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  @Override
  public Transaction add(Transaction transaction) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION)) {
      statement.setObject(1, transaction.getTransactionId());
      statement.setBigDecimal(2, transaction.getAmount());
      int clientId = transaction.getClient().getClientId();
      statement.setInt(3, clientId);
      statement.setString(4, transaction.getType().name());
      statement.setTimestamp(5, transaction.getTimestamp());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return transaction;
  }

  @Override
  public List<Transaction> findClientTransactionHistoryByClientId(int clientId) throws RepositoryException {
    List<Transaction> transactions = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement statement = connection.prepareStatement(SELECT_TRANSACTIONS_BY_CLIENT_ID)) {
      statement.setInt(1, clientId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Transaction transaction = new Transaction();
          transaction.setTransactionId(UUID.fromString(resultSet.getString("transaction_id")));
          transaction.setAmount(resultSet.getBigDecimal("amount"));
          int clientID = resultSet.getInt("client_id");
          Optional<Client> clientOptional = clientRepository.findClientById(clientID);

          if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            transaction.setClient(client);
          }
          transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
          transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
          transactions.add(transaction);
        }
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return transactions;
  }
}
