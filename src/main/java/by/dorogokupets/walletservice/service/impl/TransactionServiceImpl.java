package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.entity.TransactionType;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.repository.impl.TransactionRepositoryImpl;
import by.dorogokupets.walletservice.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository = new TransactionRepositoryImpl();

    public TransactionServiceImpl() {
    }

    @Override
    public boolean debit(Client client, BigDecimal amount, UUID transactionId) {
        if (amount.doubleValue() <= 0) {
            return false;
        }
        if (client.getBalance().compareTo(amount) < 0) {
            System.out.println("Недостаточно средств.");
            return false;
        }
        BigDecimal newBalance = client.getBalance().subtract(amount);
        client.setBalance(newBalance);
        Transaction transaction = new Transaction(client, TransactionType.DEBIT, amount, transactionId);
        transactionRepository.add(transaction);
        return true;
    }

    @Override
    public boolean credit(Client client, BigDecimal amount, UUID transactionId) {
        if (amount.doubleValue() > 0) {
            BigDecimal newBalance = client.getBalance().add(amount);
            client.setBalance(newBalance);
            Transaction transaction = new Transaction(client, TransactionType.CREDIT, amount, transactionId);
            transactionRepository.add(transaction);
            return true;
        }
        return false;
    }

    @Override
    public List<Transaction> getClientTransactionHistory(Client client) {
        String login = client.getLogin();
        List<Transaction> transactions = transactionRepository.findClientTransactionHistoryByLogin(login);
        if (transactions.isEmpty()) {
            System.out.println("У Вас, " + client.getClientFirstName() + ", нет транзакций!");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
        return transactions;
    }

}
