package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the TransactionServiceImpl class, testing the debit, credit, and getClientTransactionHistory methods.
 */
public class TransactionServiceImplTest {
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() throws Exception {
        transactionService = new TransactionServiceImpl();
    }
    /**
     * Testing the debit() method of the TransactionServiceImpl class.
     */
    @Test
    public void debit() {
        Client client = new Client();
        client.setBalance(BigDecimal.valueOf(100));
        BigDecimal amount = BigDecimal.valueOf(50);
        UUID transactionId = UUID.randomUUID();
        boolean result = transactionService.debit(client, amount, transactionId);

        assertTrue(result);
        assertEquals(BigDecimal.valueOf(50), client.getBalance());
    }
    /**
     * Testing the credit() method of the TransactionServiceImpl class.
     */
    @Test
    public void credit() {
        Client client = new Client();
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        client.setBalance(initialBalance);
        BigDecimal amount = BigDecimal.valueOf(50);
        UUID transactionId = UUID.randomUUID();
        boolean result = transactionService.credit(client, amount, transactionId);

        assertTrue(result);
        assertEquals(initialBalance.add(amount), client.getBalance());
    }
    /**
     * Testing the getClientTransactionHistory() method of the TransactionServiceImpl class.
     */
    @Test
    public void getClientTransactionHistory() {
        Client client = new Client("test", "test", new BigDecimal(10), "test", "098f6bcd4621d373cade4e832627b4f6");
        List<Transaction> transactions = transactionService.getClientTransactionHistory(client);

        assertTrue(transactions.isEmpty());
    }
}





