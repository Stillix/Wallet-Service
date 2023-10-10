package by.dorogokupets.walletservice.application;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.infrastructure.allmenu.DisplayMenu;
import by.dorogokupets.walletservice.infrastructure.allmenu.DisplayMenuImpl;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.service.impl.ClientServiceImpl;
import by.dorogokupets.walletservice.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Main {

		public static void main(String[] args) {
				ClientService clientService = new ClientServiceImpl();
				TransactionService transactionService = new TransactionServiceImpl();

				ConsoleInput consoleInput = new ConsoleInput();
				DisplayMenu displayMenu = new DisplayMenuImpl();
				String login = null;

				boolean isRunning = true;
				while (isRunning) {
						displayMenu.showMainMenu();
						int choice = consoleInput.readInt();
						switch (choice) {
								case 1:
										boolean authorization = clientService.authenticate();
										if (authorization) {
												System.out.println("Вход выполнен успешно.");
												isRunning = false;
										} else {
												System.out.println("Неверный логин или пароль.");
										}
										break;
								case 2:
										System.out.print("Введите логин: ");
										login = consoleInput.readString();
										clientService.register(login);
										break;
								default:
										System.out.println("Пожалуйста, выберите правильный пункт меню.");
										break;
						}
				}

				if (login != null) {
						boolean clientMenuRunning = true;
						Client client = clientService.findClientByLogin(login);
						while (clientMenuRunning) {
								displayMenu.showClientMenu();
								int clientChoice = consoleInput.readInt();
								switch (clientChoice) {
										case 1:
												Client currentClient = clientService.findClientByLogin(login);
												System.out.println(currentClient.toString());
												BigDecimal balance = clientService.getBalance(currentClient);
												System.out.println("Ваш текущий баланс: " + balance);
												break;
										case 2:
												System.out.print("Введите суммму пополнения: ");
												BigDecimal amount = consoleInput.readBigDecimal();
												clientService.credit(client, amount);
												break;
										case 3:
												System.out.print("Введите суммму, которую хотите снять: ");
												amount = consoleInput.readBigDecimal();
												clientService.debit(client, amount);
												break;
										case 4:
												transactionService.getClientTransactionHistory(client);
												break;
										case 5:
												clientMenuRunning = false;
												// метод главный экран
												break;
										default:
												System.out.println("Пожалуйста, выберите правильный пункт меню.");
												break;
								}
						}
				} else {
						System.out.println("Ошибка: login не установлен.");
				}
		}
}