package by.dorogokupets.walletservice.repository.impl;

import domain.entity.Client;
import domain.entity.DBProperties;
import domain.entity.Transaction;
import domain.enums.TransactionType;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;

import java.sql.*;
import java.util.*;


/**
 * Implementation of the TransactionRepository interface, representing a repository of financial transactions.
 */
public class TransactionRepositoryImpl implements TransactionRepository {

  private DBProperties dbProperties;
  private static final String INSERT_TRANSACTION = "INSERT INTO entities.transactions (amount, client_id, type,timestamp) VALUES (?, ?, ?,?)";
  private static final String SELECT_TRANSACTIONS_BY_CLIENT_ID = "SELECT * FROM entities.transactions WHERE client_id = ?";
  private ClientRepository clientRepository;

  public TransactionRepositoryImpl(ClientRepository clientRepository, DBProperties dbProperties) {
    this.dbProperties = dbProperties;
    this.clientRepository = clientRepository;
  }


  @Override
  public Transaction add(Transaction transaction) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION)) {
      statement.setInt(1, transaction.getTransactionId());
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
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(SELECT_TRANSACTIONS_BY_CLIENT_ID)) {
      statement.setInt(1, clientId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          Transaction transaction = new Transaction();
          transaction.setTransactionId(resultSet.getInt("transaction_id"));
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
