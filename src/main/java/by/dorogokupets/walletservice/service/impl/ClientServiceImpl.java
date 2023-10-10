package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.infrastructure.allmenu.DisplayMenuImpl;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.util.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

public class ClientServiceImpl implements ClientService {

		private final List<Transaction> transactions = new ArrayList<>();
		private final Map<String, Client> clients = new HashMap<>();
		private static PasswordEncoder passwordEncoder = new PasswordEncoder();
		ConsoleInput consoleInput = new ConsoleInput();

		public ClientServiceImpl() {

		}

		@Override
		public boolean register(String login) {
				if (!clients.containsKey(login)) {
						System.out.print("Введите пароль: ");
						String password = consoleInput.readString();
						System.out.print("Введите имя: ");
						String firstName = consoleInput.readString();
						System.out.print("Введите фамилию: ");
						String lastName = consoleInput.readString();
						Client client = new Client();
						client.setLogin(login);
						String digest = passwordEncoder.encode(password);
						client.setPassword(digest);
						client.setClientFirstName(firstName);
						client.setClientLastName(lastName);
						client.setBalance(BigDecimal.valueOf(0));
						clients.put(login, client);
						return true;
				} else {
						System.out.println("Клиент с таким логином уже существует!");
						return false;
				}
		}

		@Override
		public Client findClientByLogin(String login) {
				return clients.get(login);
		}

		@Override
		public boolean authenticate() {
				System.out.print("Введите логин: ");
				String login = consoleInput.readString();
				System.out.print("Введите пароль: ");
				String password = consoleInput.readString();
				Client client = this.findClientByLogin(login);
				String digest = passwordEncoder.encode(password);
				return client != null && client.getPassword().equals(digest);
		}

		@Override
		public BigDecimal getBalance(Client client) {
				return client.getBalance();
		}

		@Override
		public boolean debit(Client client, BigDecimal amount, UUID transactionId) {
				if (client.getBalance().compareTo(amount) >= 0) {
						BigDecimal newBalance = client.getBalance().subtract(amount);
						client.setBalance(newBalance);
						Transaction transaction = new Transaction(client, TransactionType.DEBIT, transactionId);
						transactions.add(transaction);
						return true;
				}
				return false;
		}

		@Override
		public boolean credit(Client client, BigDecimal amount, UUID transactionId) {
				if (amount.doubleValue() > 0) {
						BigDecimal newBalance = client.getBalance().add(amount);
						client.setBalance(newBalance);
						Transaction transaction = new Transaction(client, TransactionType.CREDIT, transactionId);
						transactions.add(transaction);
						return true;
				}
				return false;
		}

		@Override
		public List<Transaction> getClientTransactionHistory(Client client) {
				if (transactions.isEmpty()) {
						System.out.println("У Вас, " + client.getClientFirstName() + ", нет транзакций!");
				} else {
						for (Transaction transaction : transactions) {
								if (transaction.getClient().equals(client)) {
										System.out.println(transaction);
								}
						}
				}
				return transactions;
		}
}
