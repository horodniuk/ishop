package com.jshop.repository.impl;

import com.jshop.entity.Category;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.repository.CategoryRepository;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    private final ResultSetHandler<List<Category>> categoryListResultSetHandler = new DefaultListResultSetHandler<>(Category.class);

    @Override
    public List<Category> listAllCategories() {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select * from category order by id", categoryListResultSetHandler);
    }

}
