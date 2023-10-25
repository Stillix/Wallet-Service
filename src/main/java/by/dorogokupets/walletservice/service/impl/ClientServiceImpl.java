package by.dorogokupets.walletservice.service.impl;

import domain.entity.Client;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.infrastructure.in.input.ConsoleInput;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.util.PasswordEncoder;
import org.apache.commons.codec.binary.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Implementation of the ClientService interface. A service class for handling client operations, such as registration, authentication, and balance retrieval.
 */
public class ClientServiceImpl implements ClientService {

  private ConsoleInput consoleInput;
  private ClientRepository clientRepository;

  public ClientServiceImpl() {
  }

  public ClientServiceImpl(ConsoleInput consoleInput, ClientRepository clientRepository) {
    this.consoleInput = consoleInput;
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean register() throws RepositoryException {
    System.out.print("Введите логин: ");
    String login = consoleInput.readString();
    if (clientRepository.findClientByLogin(login).isEmpty()) {
      Client client = createClientFromUserInput(login);
      clientRepository.add(client);
      System.out.println("Регистрация выполнена успешно. Выполните вход.");
      return true;
    } else {
      System.out.println("Клиент с таким логином уже существует!");
      return false;
    }
  }

  private Client createClientFromUserInput(String login) {
    System.out.print("Введите пароль: ");
    String password = consoleInput.readString();
    System.out.print("Введите имя: ");
    String firstName = consoleInput.readString();
    System.out.print("Введите фамилию: ");
    String lastName = consoleInput.readString();
    Client client = new Client();
    client.setLogin(login);
    String encodedPassword = PasswordEncoder.encode(password);
    client.setPassword(encodedPassword);
    client.setClientFirstName(firstName);
    client.setClientLastName(lastName);
    client.setBalance(BigDecimal.valueOf(0));
    return client;
  }

  @Override
  public Optional<Client> findClientByLogin(String login) throws ServiceException {
    try {
      return clientRepository.findClientByLogin(login);
    } catch (RepositoryException e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public Optional<Client> findClientById(int clientId) throws ServiceException {
    try {
      return clientRepository.findClientById(clientId);
    } catch (RepositoryException e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public Optional<Client> authenticate() throws ServiceException {
    System.out.print("Введите логин: ");
    String login = consoleInput.readString();
    System.out.print("Введите пароль: ");
    String password = consoleInput.readString();
    Optional<Client> client = this.findClientByLogin(login);
    String encodedPassword = PasswordEncoder.encode(password);
    if (client.isPresent() && StringUtils.equals(client.get().getPassword(), encodedPassword)) {
      System.out.println("Вход выполнен успешно.");
      return client;
    }
    System.out.println("Неверный логин или пароль.");
    return Optional.empty();
  }

  @Override
  public BigDecimal getBalance(Client client) {
    return client.getBalance();
  }
}
