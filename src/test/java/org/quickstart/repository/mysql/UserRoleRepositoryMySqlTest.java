package org.quickstart.repository.mysql;

import org.junit.Before;
import org.junit.Test;
import org.quickstart.TestUtil;
import org.quickstart.bean.User;
import org.quickstart.components.Roles;
import org.quickstart.exceptions.DuplicateUserNameException;
import org.quickstart.exceptions.NotExistRoleException;
import org.quickstart.exceptions.NotFoundEnumRoleException;
import org.quickstart.repository.AbstractRepository;
import org.quickstart.repository.UserRepository;
import org.quickstart.repository.UserRoleRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class UserRoleRepositoryMySqlTest extends AbstractRepository {
	private UserRoleRepository userRoleRepository;
	private UserRepository userRepository;
	private int insertedUserId;

	@Before
	public void setUp() throws SQLException {
		super.setUp();
		userRoleRepository = new UserRoleRepositoryMySql();
		userRepository = new UserRepositoryMySql();

		try {
			insertNewUser();
		} catch (DuplicateUserNameException e) {
			throw new SQLException(e.getMessage(), e);
		}
	}

	private void insertNewUser() throws SQLException, DuplicateUserNameException {
		User user = TestUtil.createUserWithoutId( "newUserName", "query", "first", "last");
		insertedUserId = userRepository.createUser(user);
		user.setId(insertedUserId);
	}

	@Test
	public void getRoles_WhenExistRoles() throws SQLException, DuplicateUserNameException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.ADMIN);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		List<Roles> rolesListFromDb = userRoleRepository.getRoles(insertedUserId);

		assertThat(rolesListFromDb, containsInAnyOrder(rolesList.toArray()));
	}

	@Test
	public void getRoles_WhenEmptyRoles() throws SQLException, DuplicateUserNameException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = userRoleRepository.getRoles(insertedUserId);

		assertThat(rolesList, equalTo(new ArrayList<Roles>()));
	}

	@Test
	public void updateRoles_WhenChangeRoleFromManagerToUser() throws SQLException, DuplicateUserNameException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.ADMIN);
		rolesList.add(Roles.MANAGER);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		List<Roles> rolesListFroUpdate = new ArrayList<>();
		rolesList.add(Roles.USER);

		userRoleRepository.updateRoles(insertedUserId, rolesListFroUpdate);
		List<Roles> rolesListFromDb = userRoleRepository.getRoles(insertedUserId);

		assertThat(rolesListFromDb, containsInAnyOrder(rolesListFroUpdate.toArray()));
	}

	@Test
	public void isManager_WhenRoleAdmin() throws SQLException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.ADMIN);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		assertTrue(userRoleRepository.isManager(insertedUserId));
	}

	@Test
	public void isManager_WhenRoleManager() throws SQLException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.MANAGER);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		assertTrue(userRoleRepository.isManager(insertedUserId));
	}

	@Test
	public void isManager_WhenRoleManagerAndUser() throws SQLException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.MANAGER);
		rolesList.add(Roles.USER);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		assertTrue(userRoleRepository.isManager(insertedUserId));
	}

	@Test
	public void isManager_WhenRoleUser() throws SQLException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.USER);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		assertFalse(userRoleRepository.isManager(insertedUserId));
	}

	@Test
	public void setRoles() throws SQLException, NotExistRoleException, NotFoundEnumRoleException {
		List<Roles> rolesList = new ArrayList<>();
		rolesList.add(Roles.MANAGER);
		userRoleRepository.setRoles(insertedUserId, rolesList);

		List<Roles> rolesListFromDb = userRoleRepository.getRoles(insertedUserId);
		assertThat(rolesListFromDb, containsInAnyOrder(rolesList.toArray()));
	}
}