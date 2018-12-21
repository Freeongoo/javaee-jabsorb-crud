package org.quickstart.components.auth;

import org.quickstart.bean.User;
import org.quickstart.exceptions.InvalidAuthenticationException;
import org.quickstart.repository.UserRepository;

import javax.servlet.ServletRequest;
import java.sql.SQLException;

public interface Authentication {

	static final String USER_SESSION = "user";

	User getUserAndValidate(String userName, String password, UserRepository userRepository) throws SQLException, InvalidAuthenticationException;

	void storeUser(ServletRequest request, User user);

	User getStoredUser(ServletRequest request);
}
