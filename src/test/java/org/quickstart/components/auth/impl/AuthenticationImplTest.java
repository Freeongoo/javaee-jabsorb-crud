package org.quickstart.components.auth.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.TestUtil;
import org.quickstart.bean.User;
import org.quickstart.components.auth.Authentication;
import org.quickstart.exceptions.InvalidAuthenticationException;
import org.quickstart.repository.UserRepository;
import org.quickstart.repository.mysql.UserRepositoryMySql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationImplTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Authentication authentication = new AuthenticationImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserAndValidate_WhenValid() throws SQLException, InvalidAuthenticationException {
        String userName = "adminer";
        String password = "query";

        User expectedUser = TestUtil.createUserWithoutId(userName, password);
        when(userRepository.getUserByUserName(userName)).thenReturn(expectedUser);

        User user = authentication.getUserAndValidate(userName, password, userRepository);

        assertThat(user, equalTo(expectedUser));
    }

    @Test(expected = InvalidAuthenticationException.class)
    public void getUserAndValidate_WhenInvalidLogin() throws SQLException, InvalidAuthenticationException {
        String notExistLogin = "adminer";
        String password = "query";

        when(userRepository.getUserByUserName(notExistLogin)).thenReturn(null);

        authentication.getUserAndValidate(notExistLogin, password, userRepository);
    }

    @Test(expected = InvalidAuthenticationException.class)
    public void getUserAndValidate_WhenInvalidPassword() throws SQLException, InvalidAuthenticationException {
        String userName = "adminer";
        String password = "query";
        String wrongPassword = "invalidPassword";

        User expectedUser = TestUtil.createUserWithoutId(userName, password);
        when(userRepository.getUserByUserName(userName)).thenReturn(expectedUser);

        authentication.getUserAndValidate(userName, wrongPassword, userRepository);
    }

    @Test
    public void storeUser() {
        String userName = "adminer";
        String password = "query";
        User user = TestUtil.createUserWithoutId(userName, password);

        when(session.getAttribute(authentication.USER_SESSION)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        authentication.storeUser(request, user);

        verify(request).getSession();
        verify(session).setAttribute(authentication.USER_SESSION, user);
    }

    @Test
    public void getStoredUser_WhenCorrect() {
        String userName = "adminer";
        String password = "query";
        User expectedUser = TestUtil.createUserWithoutId(userName, password);

        when(session.getAttribute(authentication.USER_SESSION)).thenReturn(expectedUser);
        when(request.getSession(false)).thenReturn(session);

        User user = authentication.getStoredUser(request);
        assertThat(user, equalTo(expectedUser));
    }

    @Test
    public void getStoredUser_WhenNotSetInSession() {
        when(request.getSession(false)).thenReturn(session);

        User user = authentication.getStoredUser(request);
        assertThat(user, equalTo(null));
    }
}