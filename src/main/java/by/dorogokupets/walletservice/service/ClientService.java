package by.dorogokupets.walletservice.service;

import by.dorogokupets.walletservice.entity.Client;

import java.math.BigDecimal;


public interface ClientService {

   boolean register(String login);

   Client findClientByLogin(String login);

   boolean authenticate();

   BigDecimal getBalance(Client client);

   boolean debit(Client client, BigDecimal amount);

   boolean credit(Client client, BigDecimal amount);



}
