package by.dorogokupets.walletservice.service;

import by.dorogokupets.walletservice.entity.Client;

import java.math.BigDecimal;


public interface ClientService {

    boolean register();

    Client findClientByLogin(String login);

    Client authenticate();

    BigDecimal getBalance(Client client);

}
