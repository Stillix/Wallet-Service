package by.dorogokupets.walletservice.service.impl;

import by.dorogokupets.walletservice.entity.Client;
import by.dorogokupets.walletservice.entity.Transaction;
import by.dorogokupets.walletservice.infrastructure.in.ConsoleInput;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.util.PasswordEncoder;
import org.apache.commons.codec.binary.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class ClientServiceImpl implements ClientService {

    private ConsoleInput consoleInput;

    private final List<Transaction> transactions = new ArrayList<>();
    private ClientRepository clientRepository = new ClientRepositoryImpl();


    public ClientServiceImpl() {
    }

    public ClientServiceImpl(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }

    @Override
    public boolean register() {
        System.out.print("Введите логин: ");
        String login = consoleInput.readString();
        if (clientRepository.findClientByLogin(login) == null) {
            System.out.print("Введите пароль: ");
            String password = consoleInput.readString();
            System.out.print("Введите имя: ");
            String firstName = consoleInput.readString();
            System.out.print("Введите фамилию: ");
            String lastName = consoleInput.readString();
            Client client = new Client();
            client.setLogin(login);
            String encodedPassword = PasswordEncoder.encode(password);
            client.setPassword(encodedPassword);
            client.setClientFirstName(firstName);
            client.setClientLastName(lastName);
            client.setBalance(BigDecimal.valueOf(0));
            clientRepository.add(client);
            System.out.println("Регистрация выполнена успешно. Выполните вход.");
            return true;
        } else {
            System.out.println("Клиент с таким логином уже существует!");
            return false;
        }
    }

    @Override
    public Client findClientByLogin(String login) {
        return clientRepository.findClientByLogin(login);
    }

    @Override
    public Client authenticate() {
        System.out.print("Введите логин: ");
        String login = consoleInput.readString();
        System.out.print("Введите пароль: ");
        String password = consoleInput.readString();
        Client client = this.findClientByLogin(login);
        String encodedPassword = PasswordEncoder.encode(password);
        if (client != null && StringUtils.equals(client.getPassword(), encodedPassword)) {
            System.out.println("Вход выполнен успешно.");
            return client;
        }
        System.out.println("Неверный логин или пароль.");
        return null;
    }

    @Override
    public BigDecimal getBalance(Client client) {
        return client.getBalance();
    }


}
