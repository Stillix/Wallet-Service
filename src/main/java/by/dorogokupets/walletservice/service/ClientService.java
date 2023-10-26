package by.dorogokupets.walletservice.service;

import domain.dto.ClientRegistrationDto;
import domain.entity.Client;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * An interface representing the service for managing client accounts and interactions.
 */
public interface ClientService {

    /**
     * Register a new client account.
     *
     * @return true if the registration is successful, false otherwise.
     */
    boolean register(ClientRegistrationDto clientRegistrationDTO) throws RepositoryException;

    /**
     * Find a client by their login.
     *
     * @param login The login of the client to search for.
     * @return Client     The client object
     */
    Optional<Client> findClientByLogin(String login) throws ServiceException;

  Optional<Client> findClientById(int clientId) throws ServiceException;

  /**
     * Authenticate a client by their credentials (login and password).
     *
     * @return Client     The authenticated client, or null if authentication fails.
     */
    Optional<Client> authenticate(String login, String password) throws ServiceException;

    /**
     * Get the balance of a client.
     *
     * @param client     The client for whom to retrieve the balance.
     * @return BigDecimal     The client's balance.
     */
    BigDecimal getBalance(Client client);
}