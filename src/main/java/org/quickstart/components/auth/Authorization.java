package org.quickstart.components.auth;

import org.quickstart.bean.User;
import org.quickstart.components.Roles;
import org.quickstart.exceptions.InvalidValidationException;
import org.quickstart.exceptions.NotExistRoleException;
import org.quickstart.exceptions.NotFoundEnumRoleException;
import org.quickstart.exceptions.NotRulesException;

import java.sql.SQLException;
import java.util.List;

public interface Authorization {

	void validateManagerPermission() throws NotRulesException, NotFoundEnumRoleException, NotExistRoleException, SQLException;

	void validateDeletePermission(int userIdForDelete) throws InvalidValidationException;

	void validateChangeRole(User userForUpdate, List<Roles> rolesListForUpdate) throws NotFoundEnumRoleException, NotExistRoleException, SQLException, NotRulesException;

	boolean isManager(User user) throws NotFoundEnumRoleException, NotExistRoleException, SQLException;
}
