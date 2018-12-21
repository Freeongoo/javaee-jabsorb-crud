package org.quickstart.json.rpc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.TestUtil;
import org.quickstart.bean.AuthUserAndListAndRole;
import org.quickstart.bean.User;
import org.quickstart.bean.UserAndRole;
import org.quickstart.bean.UserInfoAndRole;
import org.quickstart.components.Roles;
import org.quickstart.components.auth.AuthHolder;
import org.quickstart.components.auth.Authorization;
import org.quickstart.components.validate.ValidateParams;
import org.quickstart.exceptions.*;
import org.quickstart.repository.UserRepository;
import org.quickstart.repository.UserRoleRepository;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JsonRpcUserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private Authorization authorization;

    @Mock
    private ValidateParams validateParams;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getListWithAuthInfo() throws NotFoundEnumRoleException, NotExistRoleException, SQLException {
        int userId = 1;
        String userName = "admin";
        Boolean isManager = true;
        List<User> userList = getUserList();
        User authUser = TestUtil.createUser(userId, userName);
        AuthHolder.setLoggedUser(authUser);

        AuthUserAndListAndRole expectedAuthUserAndListAndRole = new AuthUserAndListAndRole();
        expectedAuthUserAndListAndRole.setManager(isManager);
        expectedAuthUserAndListAndRole.setList(userList);
        expectedAuthUserAndListAndRole.setAuthUser(authUser);

        when(userRoleRepository.isManager(userId)).thenReturn(isManager);
        when(userRepository.getListUsers()).thenReturn(userList);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        AuthUserAndListAndRole authUserAndListAndRole = jsonRpcUser.getListWithAuthInfo(request);

        assertThat(authUserAndListAndRole, equalTo(expectedAuthUserAndListAndRole));
    }

    @Test
    public void getAuthUserInfo() throws NotFoundEnumRoleException, NotExistRoleException, SQLException {
        int userId = 1;
        String userName = "admin";
        Boolean isManager = true;
        User authUser = TestUtil.createUser(userId, userName);
        AuthHolder.setLoggedUser(authUser);

        UserAndRole expectedUserAndRole = new UserAndRole();
        expectedUserAndRole.setUser(authUser);
        expectedUserAndRole.setManager(isManager);

        when(userRoleRepository.isManager(userId)).thenReturn(isManager);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        UserAndRole userAndRole = jsonRpcUser.getAuthUserInfo(request);

        assertThat(userAndRole, equalTo(expectedUserAndRole));
    }

    @Test
    public void getUserInfoById() throws NotFoundEnumRoleException, NotExistRoleException, SQLException {
        int userId = 1;
        String userName = "admin";
        Boolean isManager = true;
        User user = TestUtil.createUser(userId, userName);

        UserAndRole expectedUserAndRole = new UserAndRole();
        expectedUserAndRole.setUser(user);
        expectedUserAndRole.setManager(isManager);

        when(userRoleRepository.isManager(userId)).thenReturn(isManager);
        when(userRepository.getUserById(userId)).thenReturn(user);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        UserAndRole userAndRole = jsonRpcUser.getUserInfoById(userId);

        assertThat(userAndRole, equalTo(expectedUserAndRole));
    }

    @Test
    public void delete() throws SQLException, NotFoundEnumRoleException, InvalidValidationException, NotExistRoleException, NotExistUserException, NotRulesException {
        int userId = 123;

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        jsonRpcUser.delete(request, userId);

        verify(authorization).validateManagerPermission();
        verify(authorization).validateDeletePermission(userId);
        verify(userRepository).deleteUser(userId);
    }

    @Test
    public void update_WhenNotChangePassword() throws NotRulesException, SQLException, NotExistRoleException, NotFoundEnumRoleException, InvalidValidationException, DuplicateUserNameException, NotExistUserException {
        int userId = 123;
        String userName = "admin";
        String firstName = "first";
        String lastName = "last";
        Boolean isManager = true;

        UserInfoAndRole userInfoAndRole = createUserInfoAndRole(isManager, userId, userName, firstName, lastName, null);

        User expectedUser = TestUtil.createUser(userId, userName, null, firstName, lastName);
        List<Roles> expectedRolesList = new ArrayList<>();
        expectedRolesList.add(Roles.MANAGER);

        when(userRepository.createUser(expectedUser)).thenReturn(userId);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        jsonRpcUser.update(request, userInfoAndRole);

        verify(authorization).validateManagerPermission();
        verify(validateParams).validateForUpdate(userInfoAndRole);
        verify(userRepository).updateUserWithoutPassword(expectedUser);
        verify(userRoleRepository).updateRoles(expectedUser.getId(), expectedRolesList);
    }

    @Test
    public void update_WhenChangePassword() throws NotRulesException, SQLException, NotExistRoleException, NotFoundEnumRoleException, InvalidValidationException, DuplicateUserNameException, NotExistUserException {
        int userId = 123;
        String userName = "admin";
        String firstName = "first";
        String lastName = "last";
        String password = "query";
        Boolean isManager = true;

        UserInfoAndRole userInfoAndRole = createUserInfoAndRole(isManager, userId, userName, firstName, lastName, password);

        User expectedUser = TestUtil.createUser(userId, userName, password, firstName, lastName);
        List<Roles> expectedRolesList = new ArrayList<>();
        expectedRolesList.add(Roles.MANAGER);

        when(userRepository.createUser(expectedUser)).thenReturn(userId);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        jsonRpcUser.update(request, userInfoAndRole);

        verify(authorization).validateManagerPermission();
        verify(validateParams).validateForUpdate(userInfoAndRole);
        verify(userRepository).updateUser(expectedUser);
        verify(userRoleRepository).updateRoles(expectedUser.getId(), expectedRolesList);
    }

    @Test
    public void create() throws DuplicateUserNameException, InvalidValidationException, NotFoundEnumRoleException, NotExistRoleException, SQLException, NotRulesException {
        int userId = 123;
        String userName = "admin";
        String firstName = "first";
        String lastName = "last";
        String password = "query";
        Boolean isManager = true;

        UserInfoAndRole userInfoAndRole = createUserInfoAndRole(isManager, userId, userName, firstName, lastName, password);

        User expectedUser = TestUtil.createUser(userId, userName, password, firstName, lastName);
        List<Roles> expectedRolesList = new ArrayList<>();
        expectedRolesList.add(Roles.MANAGER);

        when(userRepository.createUser(expectedUser)).thenReturn(userId);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(userRepository, userRoleRepository, authorization, validateParams);
        jsonRpcUser.create(request, userInfoAndRole);

        verify(authorization).validateManagerPermission();
        verify(validateParams).validateForCreate(userInfoAndRole);
        verify(userRepository).createUser(expectedUser);
        verify(userRoleRepository).setRoles(expectedUser.getId(), expectedRolesList);
    }

    private UserInfoAndRole createUserInfoAndRole(boolean isManager, int userId, String userName, String firstName, String lastName, String password) {
        UserInfoAndRole userInfoAndRole = new UserInfoAndRole();
        userInfoAndRole.setId(userId);
        userInfoAndRole.setManager(isManager);
        userInfoAndRole.setUserName(userName);
        userInfoAndRole.setFirstName(firstName);
        userInfoAndRole.setPassword(password);
        userInfoAndRole.setLastName(lastName);
        return userInfoAndRole;
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(TestUtil.createUser(2, "user", "qwerty", "first", "last"));
        userList.add(TestUtil.createUser(3, "user", "qwerty", "first", "last"));
        return userList;
    }
}