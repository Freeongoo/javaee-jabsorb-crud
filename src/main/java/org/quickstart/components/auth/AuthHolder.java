package org.quickstart.components.auth;

import org.quickstart.bean.User;

public class AuthHolder {
	private static final ThreadLocal<User> threadLocalUser = new ThreadLocal<>();

	public static User getLoggedUser() {
		return threadLocalUser.get();
	}

	public static void setLoggedUser(User user) {
		threadLocalUser.set(user);
	}

	public static boolean isLoggedUser() {
		return getLoggedUser() != null;
	}
}
