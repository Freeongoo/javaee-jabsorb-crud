package org.quickstart.components.auth.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.TestUtil;
import org.quickstart.bean.User;
import org.quickstart.components.Roles;
import org.quickstart.components.auth.AuthHolder;
import org.quickstart.components.auth.Authorization;
import org.quickstart.exceptions.InvalidValidationException;
import org.quickstart.exceptions.NotExistRoleException;
import org.quickstart.exceptions.NotFoundEnumRoleException;
import org.quickstart.exceptions.NotRulesException;
import org.quickstart.repository.UserRoleRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class AuthorizationImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private Authorization authorization = new AuthorizationImpl(userRoleRepository);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateManagerPermission_WhenUserIsManager() throws NotFoundEnumRoleException, NotExistRoleException, SQLException, NotRulesException {
        int userId = 123;
        String userName = "manager";
        User authUser = TestUtil.createUser(userId, userName);

        when(userRoleRepository.isManager(userId)).thenReturn(true);

        AuthHolder.setLoggedUser(authUser);

        authorization.validateManagerPermission();
    }

    @Test(expected = NotRulesException.class)
    public void validateManagerPermission_WhenUserIsNotManager() throws NotFoundEnumRoleException, NotExistRoleException, SQLException, NotRulesException {
        int userId = 2;
        String userName = "user";
        User authUser = TestUtil.createUser(userId, userName);

        when(userRoleRepository.isManager(userId)).thenReturn(false);

        AuthHolder.setLoggedUser(authUser);

        authorization.validateManagerPermission();
    }

    @Test
    public void validateDeletePermission_WhenLoggedUserNotEqualDeleteUser() throws InvalidValidationException {
        int userId = 2;
        int userIdForDelete = 3;
        String userName = "user";
        User authUser = TestUtil.createUser(userId, userName);

        AuthHolder.setLoggedUser(authUser);

        authorization.validateDeletePermission(userIdForDelete);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateDeletePermission_WhenLoggedUserEqualDeleteUser() throws InvalidValidationException {
        int userId = 2;
        int userIdForDelete = 2;
        String userName = "user";
        User authUser = TestUtil.createUser(userId, userName);

        AuthHolder.setLoggedUser(authUser);

        authorization.validateDeletePermission(userIdForDelete);
    }

    @Test(expected = NotRulesException.class)
    public void validateChangeRole_WhenTryRemoveManagerRulesFroSelf() throws NotExistRoleException, SQLException, NotRulesException, NotFoundEnumRoleException {
        int authUserId = 2;
        User authUser = TestUtil.createUser(authUserId, "manager");
        User userForChangeRole = TestUtil.createUser(authUserId, "changeMe");

        AuthHolder.setLoggedUser(authUser);

        when(userRoleRepository.isManager(authUserId)).thenReturn(true);

        List<Roles> listRoles = Collections.singletonList(Roles.USER);

        authorization.validateChangeRole(userForChangeRole, listRoles);
    }

    @Test
    public void validateChangeRole_WhenTryRemoveManagerRulesNotSelf() throws NotExistRoleException, SQLException, NotRulesException, NotFoundEnumRoleException {
        int authUserId = 2;
        int otherUserId = 3;
        User authUser = TestUtil.createUser(authUserId, "manager");
        User userForChangeRole = TestUtil.createUser(otherUserId, "changeMe");

        AuthHolder.setLoggedUser(authUser);

        when(userRoleRepository.isManager(authUserId)).thenReturn(true);

        List<Roles> listRoles = Collections.singletonList(Roles.USER);

        authorization.validateChangeRole(userForChangeRole, listRoles);
    }
}