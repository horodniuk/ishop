package com.jshop.framework.handler;

import com.jshop.framework.annotation.jdbc.Child;
import com.jshop.framework.converter.Converter;
import com.jshop.framework.converter.DefaultConverter;
import com.jshop.framework.exception.FrameworkSystemException;
import com.jshop.framework.utils.ReflectionUtils;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler<T> implements ResultSetHandler<T> {
    protected final Class<T> entityClass;
    protected final Converter converter;

    public DefaultResultSetHandler(Class<T> entityClass, Converter converter) {
        super();
        this.entityClass = entityClass;
        this.converter = converter;
    }

    public DefaultResultSetHandler(Class<T> entityClass) {
        this(entityClass, new DefaultConverter());
    }



    @Override
    public T handle(ResultSet rs) throws SQLException {
        try {
            T entity = entityClass.newInstance();
            List<Field> entityFields = ReflectionUtils.getAccessibleEntityFields(entityClass);
            List<String> columns = getAllColumns(rs);
            populateFields(entityFields, entity, columns, rs);
            return entity;
        } catch (InstantiationException e) {
            throw new FrameworkSystemException("Accessible constructor without parameters not found or class abstract", e);
        } catch (IllegalAccessException e) {
            throw new FrameworkSystemException(e);
        }
    }

    protected List<String> getAllColumns(ResultSet rs) throws SQLException {
        List<String> res = new ArrayList<>();
        for(int i=0;i <rs.getMetaData().getColumnCount();i++){
            res.add(rs.getMetaData().getColumnLabel(i+1));
        }
        return res;
    }

    protected void populateFields(List<Field> fields, Object entity, List<String> columns, ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
        for (Field field : fields) {
            Class<?> fieldClass = field.getType();
            Child child = field.getAnnotation(Child.class);
            if(child != null) {
                populateChildField(child, fieldClass, field, entity, rs, columns);
            } else {
                populateSimpleField(fieldClass, field, entity, rs, columns);
            }
        }
    }

    protected void populateChildField(Child child, Class<?> fieldClass, Field field, Object entity, ResultSet rs, List<String> columns)
            throws IllegalAccessException, InstantiationException, SQLException {
        Object embeddedInstance = fieldClass.newInstance();
        field.set(entity, embeddedInstance);
        List<Field> embeddedInstanceFields = ReflectionUtils.getAccessibleEntityFields(fieldClass);
        populateFields(embeddedInstanceFields, embeddedInstance, columns, rs);
        Field idField = ReflectionUtils.findField(fieldClass, embeddedInstanceFields, child.idFieldName());
        idField.set(embeddedInstance, rs.getObject(child.columnName()));
    }

    protected void populateSimpleField(Class<?> fieldClass, Field field, Object entity, ResultSet rs, List<String> columns)
            throws SQLException, IllegalAccessException {
        String columnName = ReflectionUtils.getColumnNameForField(field);
        if(columns.contains(columnName)) {
            Object valueFromDB = rs.getObject(columnName);
            Object convertedValueFromDB = converter.convert(fieldClass, valueFromDB);
            field.set(entity, convertedValueFromDB);
        }
    }

}
