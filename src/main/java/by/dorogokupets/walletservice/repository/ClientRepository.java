package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;

public interface ClientRepository {

		boolean register(Client client);

		Client findClientByLogin(String login);

		boolean authenticate(String login, String password);

}
