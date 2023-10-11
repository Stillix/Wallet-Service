package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.util.PasswordEncoder;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class ClientServiceImplTest extends TestCase {
    private ClientServiceImpl clientService;
    private ConsoleInput consoleInput;
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        consoleInput = Mockito.mock(ConsoleInput.class);
        clientRepository = new ClientRepositoryImpl();
        clientService = new ClientServiceImpl(consoleInput);
    }

    /**
     * Testing the register() method of the ClientServiceImpl class.
     */
    public void testRegister() {
        Mockito.when(consoleInput.readString()).thenReturn("testLogin", "testPassword", "TestFirstName", "TestLastName");
        boolean result = clientService.register();

        assertTrue(result);
    }

    /**
     * Testing the findClientByLogin() method of the ClientServiceImpl class.
     */
    public void testFindClientByLogin() {
        Client testClient = new Client("TestFirstName", "TestLastName", new BigDecimal(10), "test", "098f6bcd4621d373cade4e832627b4f6");
        clientRepository.add(testClient);

        Client foundClient = clientService.findClientByLogin("test");

        assertNotNull(foundClient);
        assertEquals("test", foundClient.getLogin());
        assertEquals("TestFirstName", foundClient.getClientFirstName());
        assertEquals("TestLastName", foundClient.getClientLastName());
    }

    /**
     * Testing the getBalance() method of the ClientServiceImpl class.
     */
    public void testGetBalance() {
        Client client = new Client();
        client.setBalance(BigDecimal.valueOf(100));
        BigDecimal balance = clientService.getBalance(client);

        assertEquals(BigDecimal.valueOf(100), balance);
    }
}