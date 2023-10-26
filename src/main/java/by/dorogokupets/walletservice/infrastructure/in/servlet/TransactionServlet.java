package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.validator.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.TransactionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/wallet/transaction")
public class TransactionServlet extends HttpServlet {
  private final ObjectMapper objectMapper;
  private final TransactionService transactionService;
  private final TransactionValidator transactionValidator;

  public TransactionServlet(TransactionService transactionService, TransactionValidator transactionValidator) {
    this.transactionValidator = transactionValidator;
    this.objectMapper = new ObjectMapper();
    this.transactionService = transactionService;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    TransactionDto transactionDTO = objectMapper.readValue(req.getReader(), TransactionDto.class);

    if (!transactionValidator.isValidAmount(transactionDTO.getAmount())) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("Validation failed");
      return;
    }

    boolean transactionSuccessful;
    try {
      transactionSuccessful = transactionService.processTransaction(transactionDTO);
    } catch (ServiceException e) {
      throw new RuntimeException(e);
    }

    if (transactionSuccessful) {
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().write("Transaction successful");
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write("Transaction failed");
    }
  }
}
