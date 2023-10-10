package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
		private final List<Transaction> transactions = new ArrayList<>();

		@Override
		public boolean createTransaction(Client client, TransactionType type) {
				UUID transactionId = UUID.randomUUID();
				for (Transaction transaction : transactions) {
						if (transaction.getTransactionId().equals(transactionId)) {
								return false;
						}
				}
				Transaction transaction = new Transaction(client, type,transactionId);
				transactions.add(transaction);
				return true;
		}



}
