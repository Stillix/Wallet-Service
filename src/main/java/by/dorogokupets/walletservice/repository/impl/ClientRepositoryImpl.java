package by.dorogokupets.walletservice.repository.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.repository.ClientRepository;

import java.util.HashMap;
import java.util.Map;
/**
 * Implementation of the ClientRepository interface, representing a repository of clients.
 */
public class ClientRepositoryImpl implements ClientRepository {

    private static final Map<String, Client> clients = new HashMap<>();

    @Override
    public Client add(Client client) {
        return clients.put(client.getLogin(), client);
    }

    @Override
    public Client findClientByLogin(String login) {
        return clients.get(login);
    }
}
