package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.domain.entity.Client;
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
    /**
     * Search client in repository by client id
     *
     * @param clientId - Client id
     * @return Optional Client from repository
     */

    Optional<Client> findClientById(int clientId) throws RepositoryException;
    /**
     * Update balance in database
     *
     * @param clientId,newBalance
     * @return void
     */
    void updateBalance(int clientId, BigDecimal newBalance) throws RepositoryException;
}
