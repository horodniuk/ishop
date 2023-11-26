package com.jshop.framework.annotation.jdbc;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String name() default "";
    String id() default "id";
    String nextIdExpression() default "";
}
