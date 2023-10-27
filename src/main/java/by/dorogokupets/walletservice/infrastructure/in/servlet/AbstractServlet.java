package by.dorogokupets.walletservice.infrastructure.in.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AbstractServlet extends HttpServlet {

    protected static final String CONTENT_TYPE = "application/json";
    protected static final String ENCODING = "UTF-8";

    protected void writeJsonResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.setContentType(CONTENT_TYPE);
        resp.setStatus(status);
        resp.getWriter().write(message);
    }
}
