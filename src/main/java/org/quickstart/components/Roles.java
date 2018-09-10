package org.quickstart.components;

import org.quickstart.exceptions.NotFoundEnumRoleException;

public enum  Roles {
	ADMIN(2),
	MANAGER(1),
	USER(0);

	private final int code;

	Roles(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

    static public Roles getById(int id) throws NotFoundEnumRoleException {
        for (Roles role : Roles.values()) {
            if (role.getCode() != id) continue;
            return role;
        }

        throw new NotFoundEnumRoleException("Not found enum role value by id: " + id);
    }
}
