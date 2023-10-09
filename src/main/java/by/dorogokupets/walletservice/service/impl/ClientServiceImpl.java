package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.util.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientServiceImpl implements ClientService {
		private final ClientRepository clientRepository;
		private final List<Transaction> transactions = new ArrayList<>();
		private final Map<String, Client> clients = new HashMap<>();
		private static PasswordEncoder passwordEncoder = new PasswordEncoder();

		public ClientServiceImpl(ClientRepository clientRepository) {
				this.clientRepository = clientRepository;
		}

		@Override
		public boolean register(String login, String password, String firstName, String lastName) {
				if (!clients.containsKey(login)) {
						Client client = new Client();
						client.setLogin(login);
						String digest = passwordEncoder.encode(password);
						client.setPassword(digest);
						client.setClientName(firstName);
						client.setClientLastName(lastName);
						client.setBalance(BigDecimal.valueOf(0));
						clients.put(login, client);
						return true;
				}
				return false;
		}

		@Override
		public Client findClientByLogin(String login) {
				return clients.get(login);
		}

		@Override
		public boolean authenticate(String login, String password) {
				Client client = clientRepository.findClientByLogin(login);
				String digest = passwordEncoder.encode(password);
				return client != null && client.getPassword().equals(digest);
		}

		@Override
		public BigDecimal getBalance(Client client) {
				return client.getBalance();
		}

		@Override
		public boolean debit(Client client, BigDecimal amount) {
				if (client.getBalance().compareTo(amount) >= 0) {
						BigDecimal newBalance = client.getBalance().subtract(amount);
						client.setBalance(newBalance);
						Transaction transaction = new Transaction(client, TransactionType.DEBIT);
						transactions.add(transaction);
						return true;
				}
				return false;
		}

		@Override
		public boolean credit(Client client, BigDecimal amount) {
				if (amount.doubleValue() > 0) {
						BigDecimal newBalance = client.getBalance().add(amount);
						client.setBalance(newBalance);
						Transaction transaction = new Transaction(client, TransactionType.CREDIT);
						transactions.add(transaction);
						return true;
				}
				return false;
		}

}
