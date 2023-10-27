package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.exception.RepositoryException;
import by.dorogokupets.walletservice.service.ClientService;
import domain.dto.ClientRegistrationDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/wallet/client/register")
public class ClientRegistrationServlet extends AbstractServlet {
  private final ObjectMapper objectMapper;
  private final ClientService clientService;

  public ClientRegistrationServlet(ClientService clientService) {
    this.objectMapper = new ObjectMapper();
    this.clientService = clientService;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ClientRegistrationDto clientRegistrationDTO = objectMapper.readValue(req.getReader(), ClientRegistrationDto.class);

    boolean registrationSuccessful;
    try {
      registrationSuccessful = clientService.register(clientRegistrationDTO);
    } catch (RepositoryException e) {
      writeJsonResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"{\"message\" : \"Internal Server Error\"}");
      return;
    }

    if (registrationSuccessful) {
      writeJsonResponse(resp, HttpServletResponse.SC_CREATED, "{\"message\" : \"Registration successful\"}");
    } else {
      writeJsonResponse(resp, HttpServletResponse.SC_CONFLICT, "{\"message\" : \"Registration failed\"}");
    }
  }

}
