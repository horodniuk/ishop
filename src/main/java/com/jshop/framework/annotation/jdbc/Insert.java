package com.jshop.framework.annotation.jdbc;


import com.jshop.framework.handler.DefaultResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
    Class<? extends ResultSetHandler> resultSetHandlerClass() default DefaultResultSetHandler.class;
}
