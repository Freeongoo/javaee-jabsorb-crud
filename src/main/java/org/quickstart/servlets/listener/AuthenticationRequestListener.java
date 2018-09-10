package org.quickstart.servlets.listener;

import org.quickstart.bean.User;
import org.quickstart.components.auth.AuthHolder;
import org.quickstart.components.auth.Authentication;
import org.quickstart.components.auth.impl.AuthenticationImpl;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class AuthenticationRequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
		Authentication authentication = new AuthenticationImpl();

		User authUser = authentication.getStoredUser(request);
		AuthHolder.setLoggedUser(authUser);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		AuthHolder.setLoggedUser(null);
	}
}
