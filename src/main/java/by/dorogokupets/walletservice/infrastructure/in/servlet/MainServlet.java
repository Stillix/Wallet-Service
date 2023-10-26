package by.dorogokupets.walletservice.infrastructure.in.servlet;

import by.dorogokupets.walletservice.aop.annotation.Loggable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@Loggable
@WebServlet("/health")
public class MainServlet extends HttpServlet {
  private final ObjectMapper objectMapper;


  public MainServlet() {
    this.objectMapper = new ObjectMapper();
//    this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.setContentType("application/json");
//    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(new HealthResponseDto(healthCheckService.getApplicationStatus())));
  }


}
