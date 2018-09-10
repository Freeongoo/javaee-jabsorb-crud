package org.quickstart.servlets.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.config.JSPConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeServletTest {
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private RequestDispatcher requestDispatcher;
	@InjectMocks private HomeServlet homeServlet;

	@Before
	public void setUp() {
		homeServlet = new HomeServlet();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void doGet() throws ServletException, IOException {
		String expectedJsp = JSPConfig.PATH + "index.jsp";

		when(request.getRequestDispatcher(expectedJsp)).thenReturn(requestDispatcher);
		homeServlet.doGet(request, response);

		verify(request, times(1)).getRequestDispatcher(expectedJsp);
		verify(requestDispatcher).forward(request, response);
	}
}