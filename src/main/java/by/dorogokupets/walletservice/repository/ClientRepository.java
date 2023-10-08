package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;

public interface ClientRepository {

   public boolean registerClient(String login, String password, String firstName, String lastName);

   public Client getClientByLogin(String login);

   public boolean authenticate(String login, String password);

}
