package org.quickstart.repository;

import org.quickstart.components.Roles;
import org.quickstart.exceptions.NotExistRoleException;
import org.quickstart.exceptions.NotFoundEnumRoleException;

import java.sql.SQLException;
import java.util.List;

public interface UserRoleRepository {

    List<Roles> getRoles(int userId) throws NotFoundEnumRoleException, NotExistRoleException, SQLException;

    void updateRoles(int userId, List<Roles> roles) throws NotFoundEnumRoleException, NotExistRoleException, SQLException;

    boolean isManager(int userId) throws NotFoundEnumRoleException, NotExistRoleException, SQLException;

    void setRoles(int userId, List<Roles> roles) throws SQLException;
}
