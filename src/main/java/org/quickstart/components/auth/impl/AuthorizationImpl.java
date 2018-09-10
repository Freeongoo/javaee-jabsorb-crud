package org.quickstart.components.auth.impl;

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
import java.util.List;

public class AuthorizationImpl implements Authorization {
	private UserRoleRepository userRoleRepository;

	public AuthorizationImpl(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public void validateManagerPermission() throws NotRulesException, NotFoundEnumRoleException, NotExistRoleException, SQLException {
		User authUser = AuthHolder.getLoggedUser();
		if (!isManager(authUser))
			throw new NotRulesException("Insufficient rights. You are not manager");
	}

	@Override
	public void validateDeletePermission(int userIdForDelete) throws InvalidValidationException {
		User authUser = AuthHolder.getLoggedUser();
		if (authUser.getId() == userIdForDelete)
			throw new InvalidValidationException("You can not delete yourself");
	}

	@Override
	public void validateChangeRole(User userForUpdate, List<Roles> rolesListForUpdate) throws NotFoundEnumRoleException, NotExistRoleException, SQLException, NotRulesException {
		User authUser = AuthHolder.getLoggedUser();
		if (authUser.getId() != userForUpdate.getId()) return;
		if (!isManager(authUser)) return;

		if (!rolesListForUpdate.contains(Roles.ADMIN) || !rolesListForUpdate.contains(Roles.MANAGER))
			throw new NotRulesException("You can not change for yourself not the managerial rights");
	}

	@Override
	public boolean isManager(User user) throws NotFoundEnumRoleException, NotExistRoleException, SQLException {
		return userRoleRepository.isManager(user.getId());
	}
}
