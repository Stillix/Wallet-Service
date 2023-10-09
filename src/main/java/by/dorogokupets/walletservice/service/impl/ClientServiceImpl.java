package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.util.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientService {
		private final ClientRepository clientRepository;
		private final List<Transaction> transactions = new ArrayList<>();
		private static PasswordEncoder passwordEncoder = new PasswordEncoder();

		public ClientServiceImpl(ClientRepository clientRepository) {
				this.clientRepository = clientRepository;
		}

		@Override
		public boolean register(String login, String password, String firstName, String lastName) {
				if (clientRepository.getClientByLogin(login) == null) {
						Client client = new Client();
						client.setLogin(login);
						String digest = passwordEncoder.encode(password);
						client.setPassword(digest);
						client.setClientName(firstName);
						client.setClientLastName(lastName);
						clientRepository.register(client);
						return true;
				}
				return false;
		}

		@Override
		public Client findClientByLogin(String login) {
				return clientRepository.getClientByLogin(login);
		}

		@Override
		public boolean authenticate(String login, String password) {
				Client client = clientRepository.getClientByLogin(login);
				String digest = passwordEncoder.encode(password);
				return client != null && client.getPassword().equals(digest);
		}

		@Override
		public BigDecimal getBalance(Client client) {
				return client.getBalance();
		}

		@Override
		public boolean debit(Client client, BigDecimal amount) {


						return false;

		}

		@Override
		public boolean credit(Client client, BigDecimal balance) {


				return false;
		}

		@Override
		public List<Transaction> getClientTransactionHistory(Client client) {
				List<Transaction> history = new ArrayList<>();
				for (Transaction transaction : transactions) {
						if (transaction.getClient().equals(client)) {
								history.add(transaction);
						}
				}
				return history;
		}
}
