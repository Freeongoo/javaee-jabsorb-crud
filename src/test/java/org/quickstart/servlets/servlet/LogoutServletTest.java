package org.quickstart.servlets.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogoutServletTest {
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @InjectMocks private LogoutServlet logoutServlet;

    @Before
    public void setUp() {
        logoutServlet = new LogoutServlet();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("contextPath");

        logoutServlet.doPost(request, response);

        verify(request).getSession();
        verify(session).invalidate();
        verify(response).sendRedirect(request.getContextPath());
    }
}