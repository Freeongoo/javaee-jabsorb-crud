package org.quickstart.repository.mysql;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.quickstart.TestUtil;
import org.quickstart.bean.User;
import org.quickstart.exceptions.DuplicateUserNameException;
import org.quickstart.exceptions.NotExistUserException;
import org.quickstart.repository.AbstractRepository;
import org.quickstart.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserRepositoryMySqlTest extends AbstractRepository {
    private UserRepository userRepository;
    private int insertedUserId;
    private User insertedUser;

    @Before
    public void setUp() throws SQLException {
        super.setUp();
		userRepository = new UserRepositoryMySql();

		try {
			insertNewUser();
		} catch (DuplicateUserNameException e) {
			throw new SQLException(e.getMessage(), e);
		}
    }

	private void insertNewUser() throws SQLException, DuplicateUserNameException {
		insertedUser = TestUtil.createUserWithoutId( "newUserName", "query", "first", "last");
		insertedUserId = userRepository.createUser(insertedUser);
		insertedUser.setId(insertedUserId);
	}

    @Test
    public void getListUsers() throws SQLException {
        List<User> expectedUserList = getListUserWithInsertedFromDB();
        List<User> userList = userRepository.getListUsers();

        assertThat(userList.size(), equalTo(4));
        assertThat(userList, containsInAnyOrder(expectedUserList.toArray()));
    }

    @Test
    public void getUserById_WhenInsertNewUser() throws SQLException, DuplicateUserNameException {
        User userFromDb = userRepository.getUserById(insertedUserId);
        assertThat(insertedUser, equalTo(userFromDb));
    }

    @Test
    public void getUserById_WhenNotExistUser() throws SQLException, DuplicateUserNameException {
        int notExistId = 1122233;
        User userFromDb = userRepository.getUserById(notExistId);

        assertThat(userFromDb, equalTo(null));
    }

    @Test
    public void getUserByUserName_WhenInsertNewUser() throws SQLException, DuplicateUserNameException {
        User userFromDb = userRepository.getUserByUserName(insertedUser.getUserName());
        assertThat(insertedUser, equalTo(userFromDb));
    }

    @Test
    public void getUserByUserName_WhenNotExistUser() throws SQLException, DuplicateUserNameException {
        String notExistUserName = "notExistUserName";
        User userFromDbNotExist = userRepository.getUserByUserName(notExistUserName);

        assertThat(userFromDbNotExist, equalTo(null));
    }

    @Test
    public void deleteUser_WhenInsertNewUser() throws SQLException, NotExistUserException, DuplicateUserNameException {
        List<User> expectedUserList = getDefaultListUserFromDB();
        userRepository.deleteUser(insertedUserId);
        List<User> userList = userRepository.getListUsers();

        assertThat(userList, containsInAnyOrder(expectedUserList.toArray()));
    }

    @Test(expected = NotExistUserException.class)
    public void deleteUser_WhenNotExist() throws SQLException, NotExistUserException {
        int notExistId = 1122233;
        userRepository.deleteUser(notExistId);
    }

    @Test
    public void createUser_WhenCorrectCreateNew() throws SQLException, DuplicateUserNameException {
        List<User> userList = userRepository.getListUsers();
        assertThat(userList.size(), equalTo(4));

        User userFromDb = userRepository.getUserById(insertedUserId);
        assertThat(userFromDb, equalTo(insertedUser));
    }

    @Test(expected = DuplicateUserNameException.class)
    public void createUser_WhenDuplicateUserName() throws SQLException, DuplicateUserNameException {
        userRepository.createUser(insertedUser);
    }

    @Test
    public void updateUser_WhenAllFieldsUserChange() throws SQLException, DuplicateUserNameException, NotExistUserException {
        User userForChange = TestUtil.createUserWithoutId( "other", "password", "FirstName", "LastName");
        userForChange.setId(insertedUserId);
        userRepository.updateUser(userForChange);

        User updatedUserFromDb = userRepository.getUserById(insertedUserId);
        assertThat(updatedUserFromDb, equalTo(userForChange));
    }

    @Test
    public void updateUserWithoutPassword_WhenAllFieldsUserChange() throws SQLException, DuplicateUserNameException, NotExistUserException {
        User userForChange = TestUtil.createUserWithoutId( "other", "password", "FirstName", "LastName");
        userForChange.setId(insertedUserId);
        userRepository.updateUserWithoutPassword(userForChange);

        User updatedUserFromDb = userRepository.getUserById(insertedUserId);
        userForChange.setPassword(DigestUtils.md5Hex("query"));
        assertThat(updatedUserFromDb, equalTo(userForChange));
    }

    private List<User> getListUserWithInsertedFromDB() {
        List<User> userList = getDefaultListUserFromDB();
        userList.add(insertedUser);
        return userList;
    }

    private List<User> getDefaultListUserFromDB() {
        List<User> userList = new ArrayList<>();
        userList.add(TestUtil.createUser(1, "admin", "manager", "John", "Down"));
        userList.add(TestUtil.createUser(2, "user", "user", "Mike", "Ostin"));
        userList.add(TestUtil.createUser(3, "manager", "manager", "Hilary", "Clinton"));

        return userList;
    }
}