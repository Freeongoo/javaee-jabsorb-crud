package org.quickstart.servlets.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.bean.User;
import org.quickstart.components.auth.AuthHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginFilterTest {
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;
    @InjectMocks private LoginFilter loginFilter;
    private String context;
    private String loginURI;

    @Before
    public void setUp() {
        loginFilter = new LoginFilter();
        MockitoAnnotations.initMocks(this);

        context = "context";
		loginURI = context + "/login";
    }

    @Test
    public void doFilter_WhenNotAuthUser() throws ServletException, IOException {
        AuthHolder.setLoggedUser(null);
        when(request.getContextPath()).thenReturn(context);

        loginFilter.doFilter(request, response, chain);

        verify(response).sendRedirect(loginURI);
		verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void doFilter_WhenAuthUser() throws ServletException, IOException {
        AuthHolder.setLoggedUser(new User());
        when(request.getContextPath()).thenReturn(context);

        loginFilter.doFilter(request, response, chain);

        verify(response, never()).sendRedirect(loginURI);
        verify(chain).doFilter(request, response);
    }
}