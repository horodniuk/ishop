package com.jshop.framework.annotation.jdbc;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollectionItem {
    Class<?> value();
}
