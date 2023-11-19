package com.jshop.framework.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntResultSetHandler<T> implements ResultSetHandler<Integer> {
    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }
}
