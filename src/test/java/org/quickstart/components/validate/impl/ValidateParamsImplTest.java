package org.quickstart.components.validate.impl;

import org.junit.Before;
import org.junit.Test;
import org.quickstart.bean.UserInfoAndRole;
import org.quickstart.components.validate.ValidateParams;
import org.quickstart.exceptions.InvalidValidationException;

public class ValidateParamsImplTest {
    private ValidateParams validateParams;

    @Before
    public void setUp() {
        validateParams = new ValidateParamsImpl();
    }

    @Test
    public void validateForCreate_WhenAllRequiredFieldSet() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForCreate("admin", "password", "first", "last");
        validateParams.validateForCreate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForCreate_WhenRequiredFieldEmpty() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForCreate("admin", "password", "first", "");
        validateParams.validateForCreate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForCreate_WhenRequiredFieldNull() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForCreate("admin", "password", "first", null);
        validateParams.validateForCreate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForCreate_WhenRequiredFieldSpaces() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForCreate("admin", "password", "first", "  ");
        validateParams.validateForCreate(userInfoAndRole);
    }

    @Test
    public void validateForUpdate_WhenAllRequiredFieldSet() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForUpdate(1, "admin", "first", "last");
        validateParams.validateForUpdate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForUpdate_WhenRequiredFieldEmpty() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForUpdate(1, "admin", "first", "");
        validateParams.validateForUpdate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForUpdate_WhenRequiredFieldNull() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForUpdate(1, "admin", "first", null);
        validateParams.validateForUpdate(userInfoAndRole);
    }

    @Test(expected = InvalidValidationException.class)
    public void validateForUpdate_WhenRequiredFieldSpaces() throws InvalidValidationException {
        UserInfoAndRole userInfoAndRole = createUserInfoAndRoleForUpdate(1, "admin", "first", "  ");
        validateParams.validateForUpdate(userInfoAndRole);
    }

    private UserInfoAndRole createUserInfoAndRoleForCreate(String userName, String password, String firstName, String lastName) {
        UserInfoAndRole userInfoAndRole = new UserInfoAndRole();
        userInfoAndRole.setUserName(userName);
        userInfoAndRole.setPassword(password);
        userInfoAndRole.setFirstName(firstName);
        userInfoAndRole.setLastName(lastName);
        return userInfoAndRole;
    }

    private UserInfoAndRole createUserInfoAndRoleForUpdate(int id, String userName, String firstName, String lastName) {
        UserInfoAndRole userInfoAndRole = new UserInfoAndRole();
        userInfoAndRole.setId(id);
        userInfoAndRole.setUserName(userName);
        userInfoAndRole.setFirstName(firstName);
        userInfoAndRole.setLastName(lastName);
        return userInfoAndRole;
    }
}