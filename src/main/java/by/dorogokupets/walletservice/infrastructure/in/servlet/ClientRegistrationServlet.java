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
public class ClientRegistrationServlet extends HttpServlet {
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
      throw new RuntimeException(e);
    }

    if (registrationSuccessful) {
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().write("Registration successful");
    } else {
      resp.setStatus(HttpServletResponse.SC_CONFLICT);
      resp.getWriter().write("Registration failed");
    }
  }
}
