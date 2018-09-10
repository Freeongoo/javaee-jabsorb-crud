package org.quickstart.json.rpc;

import org.quickstart.bean.*;
import org.quickstart.components.util.ConvertUtil;
import org.quickstart.components.Roles;
import org.quickstart.components.util.UserUtil;
import org.quickstart.components.util.Util;
import org.quickstart.components.auth.AuthHolder;
import org.quickstart.components.auth.Authorization;
import org.quickstart.components.validate.ValidateParams;
import org.quickstart.exceptions.*;
import org.quickstart.repository.UserRoleRepository;
import org.quickstart.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class JsonRpcUser {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private Authorization authorization;
    private ValidateParams validateParams;

    public JsonRpcUser(UserRepository userRepository, UserRoleRepository userRoleRepository,
					   Authorization authorization, ValidateParams validateParams) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authorization = authorization;
        this.validateParams = validateParams;
    }

    public AuthUserAndListAndRole getListWithAuthInfo(HttpServletRequest request) throws NotExistRoleException, NotFoundEnumRoleException, SQLException {
		AuthUserAndListAndRole authUserAndListAndRole = new AuthUserAndListAndRole();

    	User user = AuthHolder.getLoggedUser();
		authUserAndListAndRole.setAuthUser(user);

		Boolean isManager = userRoleRepository.isManager(user.getId());
		authUserAndListAndRole.setManager(isManager);

        List<User> listUsers = userRepository.getListUsers();
		listUsers = UserUtil.removePassword(listUsers);
		authUserAndListAndRole.setList(listUsers);

        return authUserAndListAndRole;
    }

    public UserAndRole getAuthUserInfo(HttpServletRequest request) throws NotExistRoleException, NotFoundEnumRoleException, SQLException {
		UserAndRole userAndRole = new UserAndRole();

		User user = AuthHolder.getLoggedUser();
		userAndRole.setUser(user);

		Boolean isManager = userRoleRepository.isManager(user.getId());
		userAndRole.setManager(isManager);
        return userAndRole;
    }

    public UserAndRole getUserInfoById(int id) throws NotFoundEnumRoleException, NotExistRoleException, SQLException {
		UserAndRole userAndRole = new UserAndRole();

		User user = userRepository.getUserById(id);
		user = UserUtil.removePassword(user);
		userAndRole.setUser(user);

		Boolean isManager = userRoleRepository.isManager(user.getId());
		userAndRole.setManager(isManager);
		return userAndRole;
    }

    public void delete(HttpServletRequest request, int id) throws NotExistUserException, NotFoundEnumRoleException, NotExistRoleException, NotRulesException, InvalidValidationException, SQLException {
        authorization.validateManagerPermission();
        authorization.validateDeletePermission(id);

        userRepository.deleteUser(id);
    }

    public void update(HttpServletRequest request, UserInfoAndRole userInfoAndRole) throws NotExistUserException, NotFoundEnumRoleException, NotExistRoleException, NotRulesException, InvalidValidationException, SQLException, DuplicateUserNameException {
		authorization.validateManagerPermission();

        validateParams.validateForUpdate(userInfoAndRole);
        User user = ConvertUtil.getUser(userInfoAndRole);

		if (Util.isEmpty(user.getPassword())) {
			userRepository.updateUserWithoutPassword(user);
		} else {
			userRepository.updateUser(user);
		}

		List<Roles> rolesList = ConvertUtil.getRoles(userInfoAndRole);
		authorization.validateChangeRole(user, rolesList);
		userRoleRepository.updateRoles(user.getId(), rolesList);
    }

    public void create(HttpServletRequest request, UserInfoAndRole userInfoAndRole) throws DuplicateUserNameException, NotExistRoleException, NotRulesException, NotFoundEnumRoleException, InvalidValidationException, SQLException {
		authorization.validateManagerPermission();

        validateParams.validateForCreate(userInfoAndRole);
        User user = ConvertUtil.getUser(userInfoAndRole);
        int userId = userRepository.createUser(user);

        List<Roles> rolesList = ConvertUtil.getRoles(userInfoAndRole);
		userRoleRepository.setRoles(userId, rolesList);
    }
}
