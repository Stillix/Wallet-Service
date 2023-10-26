package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.dto.ClientAuthenticationDto;
import domain.entity.Client;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/wallet/authenticate")
public class ClientAuthenticationServlet extends HttpServlet {
  private final ObjectMapper objectMapper;
  private final ClientService clientService;

  public ClientAuthenticationServlet(ClientService clientService) {
    this.objectMapper = new ObjectMapper();
    this.clientService = clientService;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      ClientAuthenticationDto authDTO = objectMapper.readValue(req.getReader(), ClientAuthenticationDto.class);
      String login = authDTO.getLogin();
      String password = authDTO.getPassword();

      Optional<Client> authenticatedClient = clientService.authenticate(login, password);

      if (authenticatedClient.isPresent()) {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Authentication successful");
      } else {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().write("Authentication failed");
      }
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write("Internal server error");
    }
  }
}

