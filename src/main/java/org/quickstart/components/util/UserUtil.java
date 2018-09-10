package org.quickstart.components.util;

import org.quickstart.bean.User;

import java.util.List;

public class UserUtil {
    static public List<User> removePassword(List<User> userList) {
        for (User user : userList) {
            removePassword(user);
        }
        return userList;
    }

    static public User removePassword(User user) {
        user.setPassword(null);
        return user;
    }
}
