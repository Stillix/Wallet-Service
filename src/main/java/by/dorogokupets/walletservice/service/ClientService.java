package by.dorogokupets.walletservice.service;

import by.dorogokupets.walletservice.entity.Client;

import java.math.BigDecimal;

/**
 * An interface representing the service for managing client accounts and interactions.
 */
public interface ClientService {

    /**
     * Register a new client account.
     *
     * @return true if the registration is successful, false otherwise.
     */
    boolean register();

    /**
     * Find a client by their login.
     *
     * @param login The login of the client to search for.
     * @return The client object, or null if not found.
     */
    Client findClientByLogin(String login);

    /**
     * Authenticate a client by their credentials (login and password).
     *
     * @return The authenticated client, or null if authentication fails.
     */
    Client authenticate();

    /**
     * Get the balance of a client.
     *
     * @param client The client for whom to retrieve the balance.
     * @return The client's balance.
     */
    BigDecimal getBalance(Client client);
}