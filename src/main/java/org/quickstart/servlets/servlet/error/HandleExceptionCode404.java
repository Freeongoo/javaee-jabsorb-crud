package org.quickstart.servlets.servlet.error;

import org.quickstart.config.JSPConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HandleExceptionCode404", urlPatterns = {"/AppExceptionHandler404"})
public class HandleExceptionCode404 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSPConfig.PATH + "404.jsp").forward(req, resp);
    }
}
