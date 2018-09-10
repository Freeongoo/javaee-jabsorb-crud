package org.quickstart.repository;

import org.quickstart.bean.User;
import org.quickstart.exceptions.DuplicateUserNameException;
import org.quickstart.exceptions.NotExistUserException;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
	List<User> getListUsers() throws SQLException;

	User getUserById(int id) throws SQLException;

	User getUserByUserName(String userName) throws SQLException;

	void deleteUser(int id) throws NotExistUserException, SQLException;

	int createUser(User user) throws DuplicateUserNameException, SQLException;

	void updateUser(User user) throws NotExistUserException, SQLException, DuplicateUserNameException;

	void updateUserWithoutPassword(User user) throws NotExistUserException, SQLException, DuplicateUserNameException;
}
