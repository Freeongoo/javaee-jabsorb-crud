package org.quickstart.bean;

import java.util.List;
import java.util.Objects;

public class AuthUserAndListAndRole {
	private List<User> list;
	private User authUser;
	private Boolean manager;

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getAuthUser() {
		return authUser;
	}

	public void setAuthUser(User authUser) {
		this.authUser = authUser;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUserAndListAndRole that = (AuthUserAndListAndRole) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(authUser, that.authUser) &&
                Objects.equals(manager, that.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, authUser, manager);
    }
}
