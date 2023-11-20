package com.jshop.framework.factory;

import com.jshop.framework.exception.FrameworkSystemException;

import java.sql.Connection;

public final class JDBCConnectionUtils {
    private JDBCConnectionUtils() {
    }
    private static final ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
    public static Connection getCurrentConnection() {
        Connection c = connections.get();
        if (c == null) {
            throw new FrameworkSystemException("Connection not found for current thread." +
                                               " Does your business service have @Transactional annotation?");
        }
        return c;
    }
    static void setCurrentConnection(Connection c) {
        connections.set(c);
    }
    static void removeCurrentConnection() {
        connections.remove();
    }
}
