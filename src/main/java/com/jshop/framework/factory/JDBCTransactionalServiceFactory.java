package com.jshop.framework.factory;

import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.framework.utils.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

final class JDBCTransactionalServiceFactory {
   static Object createTransactionalService(DataSource dataSource, Object realService) {
        return Proxy.newProxyInstance(JDBCTransactionalServiceFactory.class.getClassLoader(), realService.getClass().getInterfaces(),
                new TransactionalServiceInvocationHandler(realService, dataSource));
    }

    static boolean isTransactionalServiceInvocationHandler(Object invocationHandler) {
        return invocationHandler.getClass() == TransactionalServiceInvocationHandler.class;
    }

    static Object getRealService(Object invocationHandler) throws IllegalAccessException {
        TransactionalServiceInvocationHandler handler = (TransactionalServiceInvocationHandler) invocationHandler;
        return handler.realService;
    }

    private static class TransactionalServiceInvocationHandler implements InvocationHandler {
        private final JDBCTransactionalHelper transactionalHelper;
        private final Object realService;

        public TransactionalServiceInvocationHandler(Object realService, DataSource dataSource) {
            this.transactionalHelper = new JDBCTransactionalHelper(realService, dataSource);
            this.realService = realService;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if ("toString".equals(method.getName())) {
                    return "Proxy for " + realService.getClass() + " class";
                } else {
                    Method m = ReflectionUtils.findMethod(method, realService.getClass());
                    Transactional transactional = ReflectionUtils.findConfigAnnotation(m, Transactional.class);
                    if (transactional != null) {
                        return transactionalHelper.invokeTransactional(transactional, m, args);
                    } else {
                        return m.invoke(realService, args);
                    }
                }
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }

}
