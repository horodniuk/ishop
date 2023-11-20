package com.jshop.framework.annotation.jdbc;


import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {
    boolean readOnly() default true;
}
