package org.quickstart.components;

import org.junit.Test;
import org.quickstart.exceptions.NotFoundEnumRoleException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RolesTest {

    @Test
    public void getById_WhenCorrectCode() throws NotFoundEnumRoleException {
        int adminCode = Roles.ADMIN.getCode();
        Roles roles = Roles.getById(adminCode);
        assertThat(roles, equalTo(Roles.ADMIN));
    }

    @Test(expected = NotFoundEnumRoleException.class)
    public void getById_WhenNotExistCode() throws NotFoundEnumRoleException {
        int notExistCode = -1;
        Roles roles = Roles.getById(notExistCode);
    }
}