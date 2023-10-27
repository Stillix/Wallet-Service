package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.aop.annotation.Loggable;
import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.repository.impl.TransactionRepositoryImpl;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.service.impl.ClientServiceImpl;
import by.dorogokupets.walletservice.util.DbConfig;
import by.dorogokupets.walletservice.domain.dto.ClientRegistrationDto;
import by.dorogokupets.walletservice.domain.entity.DBProperties;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Loggable
@WebServlet("/wallet/client/register")
public class ClientRegistrationServlet extends HttpServlet {
  private static Logger logger = LogManager.getLogger();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private ClientService clientService;

  public ClientRegistrationServlet() {
    ClientRepository clientRepository = new ClientRepositoryImpl(DbConfig.dbProperties);
    this.clientService = new ClientServiceImpl(clientRepository);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    ClientRegistrationDto clientRegistrationDTO = objectMapper.readValue(req.getReader(), ClientRegistrationDto.class);

    boolean registrationSuccessful;
    try {
      registrationSuccessful = clientService.register(clientRegistrationDTO);
    } catch (RepositoryException e) {
      throw new RuntimeException(e);
    }

    if (registrationSuccessful) {
      logger.log(Level.INFO, "Registration successful");
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().write("Registration successful");
    } else {
      logger.log(Level.INFO, "Registration failed");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("Registration failed");
    }
  }
}
