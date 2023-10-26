package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.aop.annotation.Loggable;
import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.repository.ClientRepository;
import by.dorogokupets.walletservice.repository.TransactionRepository;
import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import by.dorogokupets.walletservice.repository.impl.TransactionRepositoryImpl;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.service.impl.TransactionServiceImpl;
import by.dorogokupets.walletservice.util.DbConfig;
import by.dorogokupets.walletservice.validator.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.TransactionDto;
import domain.entity.DBProperties;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Loggable
@WebServlet("/wallet/transaction")
public class TransactionServlet extends HttpServlet {
  private static Logger logger = LogManager.getLogger();
  private TransactionService transactionService;
  private TransactionValidator transactionValidator;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void init() {
    ClientRepository clientRepository = new ClientRepositoryImpl(DbConfig.dbProperties);
    TransactionRepository transactionRepository = new TransactionRepositoryImpl(clientRepository, DbConfig.dbProperties);
    transactionValidator = new TransactionValidator();
    transactionService = new TransactionServiceImpl(transactionRepository, clientRepository);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    TransactionDto transactionDTO = objectMapper.readValue(req.getReader(), TransactionDto.class);

    if (!transactionValidator.isValidAmount(transactionDTO.getAmount())) {
      logger.log(Level.INFO, "Validation failed");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("Validation failed");
      return;
    }

    boolean transactionSuccessful = false;
    try {
      transactionSuccessful = transactionService.processTransaction(transactionDTO);
    } catch (ServiceException e) {
      logger.log(Level.INFO, "Транзакция не проведена");
    }
    if (transactionSuccessful) {
      logger.log(Level.INFO, "Transaction successful");
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().write("Transaction successful");
    } else {
      logger.log(Level.INFO, "Transaction failed");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write("Transaction failed");
    }
  }
}
