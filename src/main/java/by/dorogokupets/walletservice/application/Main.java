package by.dorogokupets.walletservice.application;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.infrastructure.menu.DisplayMenu;
import by.dorogokupets.walletservice.infrastructure.menu.impl.DisplayMenuImpl;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.service.impl.ClientServiceImpl;
import by.dorogokupets.walletservice.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Darahakupets
 */

public class Main {

    public static void main(String[] args) {
        ConsoleInput consoleInput = new ConsoleInput();
        ClientService clientService = new ClientServiceImpl(consoleInput);
        TransactionService transactionService = new TransactionServiceImpl();


        DisplayMenu displayMenu = new DisplayMenuImpl();
        Client currentClient = null;

        while (true) {
            while (currentClient == null) {
                displayMenu.showMainMenu();
                int choice = consoleInput.readInt();
                switch (choice) {
                    case 1:
                        currentClient = clientService.authenticate();
                        break;
                    case 2:
                        clientService.register();
                        break;
                    case 3:
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Пожалуйста, выберите правильный пункт меню.");
                        break;
                }


            }

            while (currentClient != null) {
                displayMenu.showClientMenu();
                int clientChoice = consoleInput.readInt();
                switch (clientChoice) {
                    case 1:
                        System.out.println(currentClient);
                        break;
                    case 2:
                        UUID transactionId = UUID.randomUUID();
                        System.out.println("Сгенерирован уникальный идентификатор транзакции: " + transactionId);
                        System.out.print("Введите сумму пополнения: ");
                        BigDecimal creditAmount = consoleInput.readBigDecimal();
                        consoleInput.readString();
                        if (transactionService.credit(currentClient, creditAmount, transactionId)) {
                            System.out.println("Вы успешно пополнили счет!");
                        } else {
                            System.out.println("Операция пополнения средств не произведена.");
                        }
                        break;
                    case 3:
                        transactionId = UUID.randomUUID();
                        System.out.println("Сгенерирован уникальный идентификатор транзакции: " + transactionId);
                        System.out.print("Введите сумму, которую хотите снять: ");
                        BigDecimal debitAmount = consoleInput.readBigDecimal();
                        consoleInput.readString();
                        if (transactionService.debit(currentClient, debitAmount, transactionId)) {
                            System.out.println("Вы успешно сняли деньги со счета!");
                        } else {
                            System.out.println("Операция снятия средств не произведена.");
                        }
                        break;
                    case 4:
                        System.out.println("История транзакций");
                        transactionService.getClientTransactionHistory(currentClient);
                        break;
                    case 5:
                        currentClient = null;
                        break;
                    default:
                        System.out.println("Пожалуйста, выберите правильный пункт меню.");
                        break;
                }
            }
        }

    }
}