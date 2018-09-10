package org.quickstart.db;

import java.sql.Connection;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> threadLocalScope = new  ThreadLocal<>();

    public static Connection get() {
        return threadLocalScope.get();
    }

    public static void set(Connection conn) {
        threadLocalScope.set(conn);
    }
}
