package com.jshop.framework.factory;

import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.framework.exception.FrameworkSystemException;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class JDBCTransactionalHelper {
    private final Object realService;
    private final DataSource dataSource;

    JDBCTransactionalHelper(Object realService, DataSource dataSource) {
        this.realService = realService;
        this.dataSource = dataSource;
    }

    Object invokeTransactional(Transactional transactional, Method method, Object[] args) throws Exception {
        try (Connection c = dataSource.getConnection()) {
            JDBCConnectionUtils.setCurrentConnection(c);
            if (transactional.readOnly()) {
                return method.invoke(realService, args);
            } else {
                return invokeNotReadOnlyTransactional(c, method, args);
            }
        } catch (SQLException e) {
            throw new FrameworkSystemException(e);
        } finally {
            JDBCConnectionUtils.removeCurrentConnection();
        }
    }

    private Object invokeNotReadOnlyTransactional(Connection c, Method method, Object[] args) throws Exception {
        try {
            TransactionSynchronizationManager.initSynchronization();
            Object result = method.invoke(realService, args);
            c.commit();
            afterCommit();
            return result;
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                c.rollback();
            } else {
                c.commit();
            }
            throw e;
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

     private void afterCommit() {
         List<TransactionSynchronization> synchronizations = TransactionSynchronizationManager.getSynchronizations();
         for (TransactionSynchronization synchronization : synchronizations) {
             synchronization.afterCommit();
         }
     }
}
