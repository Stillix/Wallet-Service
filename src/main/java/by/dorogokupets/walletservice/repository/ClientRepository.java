package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;

public interface ClientRepository {

    /**
     * @param client
     * @return
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
