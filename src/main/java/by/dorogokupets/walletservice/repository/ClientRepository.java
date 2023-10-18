package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;

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
    Client add(Client client);

    /**
     * Search client in repository by login
     *
     * @param login - Client login
     * @return Client from repository
     */
    Client findClientByLogin(String login);


}
