package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ClientRepository {

		boolean register(String login);

		Client findClientByLogin(String login);

		boolean authenticate();

		BigDecimal getBalance(Client client);

		boolean debit(Client client, BigDecimal amount, UUID transactionId);

		boolean credit(Client client, BigDecimal amount, UUID transactionId);


		List<Transaction> getClientTransactionHistory(Client client);

}
