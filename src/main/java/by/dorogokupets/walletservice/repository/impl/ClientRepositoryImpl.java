package by.dorogokupets.walletservice.repository.impl;

import by.dorogokupets.walletservice.domain.entity.Client;
import by.dorogokupets.walletservice.domain.entity.DBProperties;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.repository.ClientRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

/**
 * Implementation of the ClientRepository interface.
 */
public class ClientRepositoryImpl implements ClientRepository {

  private final DBProperties dbProperties;
  private static final String INSERT_CLIENT = "INSERT INTO entities.clients (client_id,login, password, first_name, last_name, balance) VALUES (nextval('entities.client_sequence'),?, ?, ?, ?, ?)";
  private static final String SELECT_CLIENT_BY_LOGIN = "SELECT * FROM entities.clients WHERE login = ?";
  private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM entities.clients WHERE client_id = ?";
  private static final String UPDATE_BALANCE = "UPDATE entities.clients SET balance = ? WHERE client_id = ?";

  public ClientRepositoryImpl(DBProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  @Override
  public Optional<Client> add(Client client) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, client.getLogin());
      statement.setString(2, client.getPassword());
      statement.setString(3, client.getClientFirstName());
      statement.setString(4, client.getClientLastName());
      statement.setBigDecimal(5, client.getBalance());
      statement.executeUpdate();
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          client.setClientId(generatedKeys.getInt(1));
        }
      }
      return Optional.of(client);
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
  }

  @Override
  public Optional<Client> findClientByLogin(String login) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_BY_LOGIN)) {
      statement.setString(1, login);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          Client client = getClient(resultSet);
          return Optional.of(client);
        }
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public Optional<Client> findClientById(int clientId) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_BY_ID)) {
      statement.setInt(1, clientId);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          Client client = getClient(resultSet);
          return Optional.of(client);
        }
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public void updateBalance(int clientId, BigDecimal newBalance) throws RepositoryException {
    try (Connection connection = DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
         PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE)) {
      statement.setBigDecimal(1, newBalance);
      statement.setInt(2, clientId);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
  }

  private Client getClient(ResultSet resultSet) throws SQLException {
    Client client = new Client();
    client.setClientId(resultSet.getInt("client_id"));
    client.setLogin(resultSet.getString("login"));
    client.setPassword(resultSet.getString("password"));
    client.setClientFirstName(resultSet.getString("first_name"));
    client.setClientLastName(resultSet.getString("last_name"));
    client.setBalance(resultSet.getBigDecimal("balance"));
    return client;
  }
}
