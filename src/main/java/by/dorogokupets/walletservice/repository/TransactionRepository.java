package by.dorogokupets.walletservice.repository;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;

import java.util.List;

public interface TransactionRepository {


		boolean createTransaction(Client client, TransactionType type);

		List<Transaction> getClientTransactionHistory(Client client);
}
