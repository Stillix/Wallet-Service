package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.infrastructure.out.mapper.ClientMapper;
import by.dorogokupets.walletservice.domain.dto.ClientRegistrationDto;
import by.dorogokupets.walletservice.domain.entity.Client;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.exception.ServiceException;
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

  private final ClientRepository clientRepository;

  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public boolean register(ClientRegistrationDto clientRegistrationDTO) throws RepositoryException {
    String login = clientRegistrationDTO.getLogin();
    if (clientRepository.findClientByLogin(login).isPresent()) {
      Client client = createClientFromRegistrationDTO(clientRegistrationDTO);
      clientRepository.add(client);
      System.out.println("Регистрация выполнена успешно. Выполните вход.");
      return true;
    } else {
      System.out.println("Клиент с таким логином уже существует!");
      return false;
    }
  }

  private Client createClientFromRegistrationDTO(ClientRegistrationDto clientRegistrationDTO) {
    Client client = ClientMapper.MAPPER.mapToClient(clientRegistrationDTO);
    client.setBalance(BigDecimal.valueOf(0));
    return client;
  }
//  @Override
//  public boolean register(ClientRegistrationDto clientRegistrationDTO) throws RepositoryException {
//    String login = clientRegistrationDTO.getLogin();
//    if (clientRepository.findClientByLogin(login).isPresent()) {
//      Client client = createClientFromRegistrationDTO(clientRegistrationDTO);
//      clientRepository.add(client);
//      System.out.println("Регистрация выполнена успешно. Выполните вход.");
//      return true;
//    } else {
//      System.out.println("Клиент с таким логином уже существует!");
//      return false;
//    }
//  }
//
//  private Client createClientFromRegistrationDTO(ClientRegistrationDto clientRegistrationDTO) {
//    Client client = new Client();
//    client.setLogin(clientRegistrationDTO.getLogin());
//    String encodedPassword = PasswordEncoder.encode(clientRegistrationDTO.getPassword());
//    client.setPassword(encodedPassword);
//    client.setClientFirstName(clientRegistrationDTO.getClientFirstName());
//    client.setClientLastName(clientRegistrationDTO.getClientLastName());
//    client.setBalance(BigDecimal.valueOf(0));
//    return client;
//  }

//  @Override
//  public boolean register() throws RepositoryException {
//    System.out.print("Введите логин: ");
//    String login = consoleInput.readString();
//    if (clientRepository.findClientByLogin(login).isEmpty()) {
//      Client client = createClientFromUserInput(login);
//      clientRepository.add(client);
//      System.out.println("Регистрация выполнена успешно. Выполните вход.");
//      return true;
//    } else {
//      System.out.println("Клиент с таким логином уже существует!");
//      return false;
//    }
//  }

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
  public Optional<Client> authenticate(String login, String password) throws ServiceException {
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
