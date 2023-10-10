package by.dorogokupets.walletservice.repository.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.repository.ClientRepository;

import java.util.HashMap;
import java.util.Map;

public class ClientRepositoryImpl implements ClientRepository {

    private static final Map<String, Client> clients = new HashMap<>();

//    static {
//        clients.put("test", new Client("test", "test", new BigDecimal(10), "test", "098f6bcd4621d373cade4e832627b4f6"));
//    }

    @Override
    public Client add(Client client) {
        return clients.put(client.getLogin(), client);
    }

    @Override
    public Client findClientByLogin(String login) {
        return clients.get(login);
    }
}
