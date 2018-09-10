package org.quickstart.servlets.filter;

import org.quickstart.components.auth.AuthHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/", "/logout"})
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String loginURI = request.getContextPath() + "/login";

		if (!AuthHolder.isLoggedUser()) {
            response.sendRedirect(loginURI);
            return;
        }

        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
