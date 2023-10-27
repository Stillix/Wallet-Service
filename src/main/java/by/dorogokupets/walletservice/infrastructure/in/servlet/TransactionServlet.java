package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.exception.ServiceException;
import by.dorogokupets.walletservice.service.TransactionService;
import by.dorogokupets.walletservice.validator.TransactionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import  by.dorogokupets.walletservice.domain.dto.TransactionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/wallet/transaction")
public class TransactionServlet extends AbstractServlet {
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
      writeJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "{\"message\" : \"Validation failed\"}");
      return;
    }

    boolean transactionSuccessful = false;
    try {
      transactionSuccessful = transactionService.processTransaction(transactionDTO);
    } catch (ServiceException e) {
      writeJsonResponse(resp, HttpServletResponse.SC_CREATED, "{\"message\" : \"Internal Server Error\"}");
      return;
    }
    if (transactionSuccessful) {
      writeJsonResponse(resp, HttpServletResponse.SC_CREATED, "{\"message\" : \"Transaction successful\"}");
    } else {
      writeJsonResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"message\" : \"Transaction failed\" }");
    }
  }

}
