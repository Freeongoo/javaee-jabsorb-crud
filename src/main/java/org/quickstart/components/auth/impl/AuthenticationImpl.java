package org.quickstart.components.auth.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.quickstart.bean.User;
import org.quickstart.components.auth.Authentication;
import org.quickstart.exceptions.InvalidAuthenticationException;
import org.quickstart.repository.UserRepository;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AuthenticationImpl implements Authentication {

    @Override
    public User getUserAndValidate(String userName, String password, UserRepository userRepository) throws SQLException, InvalidAuthenticationException {
        User user = userRepository.getUserByUserName(userName);

        if (user == null)
            throw new InvalidAuthenticationException("Cannot find login");

        String encodedEnteredPassword = DigestUtils.md5Hex(password);

        if (!encodedEnteredPassword.equals(user.getPassword()))
            throw new InvalidAuthenticationException("Invalid password");

        return user;
    }

    @Override
    public void storeUser(ServletRequest request, User user) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.getSession().setAttribute(USER_SESSION, user);
    }

    public User getStoredUser(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session == null)
            return null;

        return (User) session.getAttribute(USER_SESSION);
    }
}
