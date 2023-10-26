package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.aop.annotation.Loggable;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.repository.impl.TransactionRepositoryImpl;
import by.dorogokupets.walletservice.service.ClientService;
import by.dorogokupets.walletservice.service.impl.ClientServiceImpl;
import by.dorogokupets.walletservice.service.impl.TransactionServiceImpl;
import by.dorogokupets.walletservice.util.DbConfig;
import by.dorogokupets.walletservice.validator.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.ClientAuthenticationDto;
import domain.entity.Client;
import domain.entity.DBProperties;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Loggable
@WebServlet("/wallet/authenticate")
public class ClientAuthenticationServlet extends HttpServlet {
  private static Logger logger = LogManager.getLogger();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private ClientService clientService;

  public ClientAuthenticationServlet() {
    ClientRepository clientRepository = new ClientRepositoryImpl(DbConfig.dbProperties);
    this.clientService = new ClientServiceImpl(clientRepository);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    try {
      ClientAuthenticationDto authDTO = objectMapper.readValue(req.getReader(), ClientAuthenticationDto.class);
      String login = authDTO.getLogin();
      String password = authDTO.getPassword();

      Optional<Client> authenticatedClient = clientService.authenticate(login, password);

      if (authenticatedClient.isPresent()) {
        resp.setStatus(HttpServletResponse.SC_OK);
        logger.log(Level.INFO, "Authentication successful");
        resp.getWriter().write("Authentication successful");
      } else {
        logger.log(Level.INFO, "Authentication failed");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("Authentication failed");
      }
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write("Internal server error");
    }
  }
}

