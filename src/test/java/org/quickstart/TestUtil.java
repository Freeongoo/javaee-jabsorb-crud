package org.quickstart;

import org.apache.commons.codec.digest.DigestUtils;
import org.quickstart.bean.User;

public class TestUtil {

    public static User createUserWithoutId(String userName, String password, String firstName, String lastName) {
        User user = new User();
        user.setUserName(userName);
        if (password == null) {
            user.setPassword(null);
        } else {
            user.setPassword(DigestUtils.md5Hex(password));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public static User createUserWithoutId(String userName, String password) {
        return createUserWithoutId(userName, password, null, null);
    }

    public static User createUser(int id, String userName, String password, String firstName, String lastName) {
        User user = createUserWithoutId(userName, password, firstName, lastName);
        user.setId(id);
        return user;
    }

    public static User createUser(int id, String userName) {
        return createUser(id, userName, null, null, null);
    }
}
