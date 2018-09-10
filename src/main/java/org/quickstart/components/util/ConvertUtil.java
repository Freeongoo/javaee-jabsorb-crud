package org.quickstart.components.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.quickstart.bean.User;
import org.quickstart.bean.UserInfoAndRole;
import org.quickstart.components.Roles;

import java.util.Collections;
import java.util.List;

public class ConvertUtil {
	public static User getUser(UserInfoAndRole userInfoAndRole) {
		User user = new User();

		user.setId(userInfoAndRole.getId());
		String password = userInfoAndRole.getPassword();

		// correction password
		if (Util.isEmpty(password)) {
			user.setPassword(null);
		} else {
			user.setPassword(DigestUtils.md5Hex(password));
		}

		user.setUserName(userInfoAndRole.getUserName());
		user.setFirstName(userInfoAndRole.getFirstName());
		user.setLastName(userInfoAndRole.getLastName());

		return user;
	}

	public static List<Roles> getRoles(UserInfoAndRole userInfoAndRole) {
		Roles role = getRole(userInfoAndRole.getManager());
		return Collections.singletonList(role);
	}

	private static Roles getRole(boolean manager) {
		if (manager) return Roles.MANAGER;
		return Roles.USER;
	}
}
