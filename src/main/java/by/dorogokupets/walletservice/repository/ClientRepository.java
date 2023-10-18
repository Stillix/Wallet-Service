package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.exception.RepositoryException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * An interface representing a repository for managing client data.
 */
public interface ClientRepository {

    /**
     * Add client to repository
     *
     * @param client
     * @return Client
     */
    Optional<Client> add(Client client) throws RepositoryException;

    /**
     * Search client in repository by login
     *
     * @param login - Client login
     * @return Client from repository
     */
    Optional<Client> findClientByLogin(String login) throws RepositoryException;


    Optional<Client> findClientById(int clientId) throws RepositoryException;

    void updateBalance(int clientId, BigDecimal newBalance) throws RepositoryException;
}
