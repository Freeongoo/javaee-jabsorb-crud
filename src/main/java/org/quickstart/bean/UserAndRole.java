package org.quickstart.bean;

import java.util.Objects;

public class UserAndRole {
	private User user;
	private Boolean manager;

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAndRole that = (UserAndRole) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(manager, that.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, manager);
    }
}
