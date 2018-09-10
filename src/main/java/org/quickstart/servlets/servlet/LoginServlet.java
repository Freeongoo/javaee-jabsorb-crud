package org.quickstart.servlets.servlet;

import org.quickstart.bean.User;
import org.quickstart.components.auth.impl.AuthenticationImpl;
import org.quickstart.config.JSPConfig;
import org.quickstart.exceptions.InvalidAuthenticationException;
import org.quickstart.repository.mysql.UserRepositoryMySql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private AuthenticationImpl authentication;

	@Override
	public void init() throws ServletException {
		super.init();
		authentication = new AuthenticationImpl();
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get request parameters for username and password
		String userName = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();

		try {
			User user = authentication.getUserAndValidate(userName, password, new UserRepositoryMySql());
            authentication.storeUser(request, user);
            response.sendRedirect(request.getContextPath() + "/");
		} catch (InvalidAuthenticationException e) {
			request.setAttribute("error", e.getMessage());
			request.setAttribute("username", userName);  // save login if insert
			request.getRequestDispatcher(JSPConfig.PATH + "login.jsp").forward(request, response);
		} catch (SQLException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSPConfig.PATH + "login.jsp").forward(request, response);
    }
}