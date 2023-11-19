package com.jshop.framework.converter;

import com.jshop.framework.exception.FrameworkSystemException;

public class DefaultConverter implements Converter {
    @Override
    public <T> T convert(Class<T> entityClass, Object value) {
        if (value == null) {
            return null;
        } else if (entityClass == Object.class || entityClass == value.getClass()) {
            return (T) value;
        } else if (entityClass == String.class) {
            return (T) value.toString();
        } else {
            throw new FrameworkSystemException("Can't convert class " + value.getClass() +
                                               " to class " +
                                               entityClass);
        }
    }
}
