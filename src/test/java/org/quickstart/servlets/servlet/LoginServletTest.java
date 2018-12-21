package org.quickstart.servlets.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.TestUtil;
import org.quickstart.components.auth.impl.AuthenticationImpl;
import org.quickstart.config.JSPConfig;
import org.quickstart.bean.User;
import org.quickstart.exceptions.InvalidAuthenticationException;
import org.quickstart.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    private static String pathJsp = JSPConfig.PATH + "login.jsp";

    @Mock
    private AuthenticationImpl authentication;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private LoginServlet loginServlet = new LoginServlet();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPost_WhenCorrectLogin() throws IOException, ServletException, InvalidAuthenticationException, SQLException {
        String userName = "admin";
        String password = "manager";
        String context = "context";

        User authUser = TestUtil.createUserWithoutId(userName, password);

        when(request.getContextPath()).thenReturn(context);
        when(authentication.getUserAndValidate(userName, password, userRepository)).thenReturn(authUser);
        when(request.getParameter("username")).thenReturn(userName);
        when(request.getParameter("password")).thenReturn(password);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect(context + "/");
    }

    @Test
    public void doPost_WhenInvalidLogin() throws IOException, ServletException, InvalidAuthenticationException, SQLException {
        String userName = "admin";
        String password = "manager";
        String invalidLogin = "notExistLogin";
        String context = "context";
        String errorMsg = "Cannot find login";

        User authUser = TestUtil.createUserWithoutId(userName, password);

        when(request.getContextPath()).thenReturn(context);
        when(authentication.getUserAndValidate(userName, password, userRepository)).thenReturn(authUser);

        when(request.getParameter("username")).thenReturn(invalidLogin);
        when(request.getParameter("password")).thenReturn(password);

        doThrow(new InvalidAuthenticationException(errorMsg))
                .when(authentication)
                .getUserAndValidate(any(), any(), any());

        when(request.getRequestDispatcher(pathJsp))
                .thenReturn(requestDispatcher);

        loginServlet.doPost(request, response);

        verify(request).setAttribute("error", errorMsg);
        verify(request).setAttribute("username", invalidLogin);
        verify(request, never()).getSession();
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(pathJsp))
                .thenReturn(requestDispatcher);

        loginServlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(pathJsp);
        verify(request, never()).getSession();
        verify(requestDispatcher).forward(request, response);
    }
}