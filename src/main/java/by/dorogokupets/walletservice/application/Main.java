package by.dorogokupets.walletservice.application;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.DBProperties;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.infrastructure.menu.DisplayMenu;
import by.dorogokupets.walletservice.infrastructure.menu.impl.DisplayMenuImpl;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.liquibase.Liquibase;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.repository.impl.TransactionRepositoryImpl;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.service.impl.ClientServiceImpl;
import by.dorogokupets.walletservice.service.impl.TransactionServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Darahakupets
 */

public class Main {

  private static final DBProperties dbProperties;

  static {
    Properties prop;
    try (InputStream input = ClientRepositoryImpl.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
      prop = new Properties();
      prop.load(input);
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
    dbProperties = new DBProperties(
            prop.getProperty("url"),
            prop.getProperty("username"),
            prop.getProperty("password")
    );
  }
  private static Logger logger = LogManager.getLogger();
  static ClientRepository clientRepository = new ClientRepositoryImpl(dbProperties);
  static TransactionRepository transactionRepository = new TransactionRepositoryImpl(clientRepository, dbProperties);

  public static void main(String[] args) {
    Liquibase.runMigrations();

    ConsoleInput consoleInput = new ConsoleInput();
    ClientService clientService = new ClientServiceImpl(consoleInput, clientRepository);
    TransactionService transactionService = new TransactionServiceImpl(transactionRepository, clientRepository);
    DisplayMenu displayMenu = new DisplayMenuImpl();
    Optional<Client> currentClient = Optional.empty();

    while (true) {
      while (currentClient.isEmpty()) {
        displayMenu.showMainMenu();
        int choice = consoleInput.readInt();
        switch (choice) {
          case 1:
            try {
              logger.log(Level.INFO, "Авторизация...");
              currentClient = clientService.authenticate();
            } catch (ServiceException e) {
              System.out.println("Ошибка авторизации.");
            }
            break;
          case 2:
            try {
              logger.log(Level.INFO, "Регистрация...");
              clientService.register();
            } catch (RepositoryException e) {
              System.out.println("Ошибка регистрации.");
            }
            break;
          case 3:
            System.exit(1);
            break;
          default:
            System.out.println("Пожалуйста, выберите правильный пункт меню.");
            break;
        }
      }

      while (!currentClient.isEmpty()) {
        displayMenu.showClientMenu();
        int clientChoice = consoleInput.readInt();
        switch (clientChoice) {
          case 1:
            System.out.println(currentClient.get());
            logger.log(Level.INFO, "Проверка баланса");
            break;
          case 2:
            logger.log(Level.INFO, "Пополнение баланса...");
            UUID transactionId = UUID.randomUUID();
            System.out.println("Сгенерирован уникальный идентификатор транзакции: " + transactionId);
            System.out.print("Введите сумму пополнения: ");
            BigDecimal creditAmount = consoleInput.readBigDecimal();
            consoleInput.readString();
            try {
              if (transactionService.credit(currentClient.get(), creditAmount, transactionId)) {
                System.out.println("Вы успешно пополнили счет!");
              } else {
                System.out.println("Операция пополнения средств не произведена.");
              }
            } catch (ServiceException e) {
              System.out.println("Ошибка пополнения счета!");
            }
            break;
          case 3:
            logger.log(Level.INFO, "Снятие средств со счета...");
            transactionId = UUID.randomUUID();
            System.out.println("Сгенерирован уникальный идентификатор транзакции: " + transactionId);
            System.out.print("Введите сумму, которую хотите снять: ");
            BigDecimal debitAmount = consoleInput.readBigDecimal();
            consoleInput.readString();
            try {
              if (transactionService.debit(currentClient.get(), debitAmount, transactionId)) {
                System.out.println("Вы успешно сняли деньги со счета!");
              } else {
                System.out.println("Операция снятия средств не произведена.");
              }
            } catch (ServiceException e) {
              System.out.println("Ошибка снятия средств со счета!");
            }
            break;
          case 4:
            System.out.println("История транзакций");
            try {
              transactionService.getClientTransactionHistory(currentClient.get());
            } catch (ServiceException e) {
              System.out.println("Ошибка получения истории транзакций клиента!");
            }
            break;
          case 5:
            currentClient = Optional.empty();
            logger.log(Level.INFO, "Выход...");
            System.out.println("Выход успешно выполнен.");
            break;
          default:
            System.out.println("Пожалуйста, выберите правильный пункт меню.");
            break;
        }
      }
    }
  }
}