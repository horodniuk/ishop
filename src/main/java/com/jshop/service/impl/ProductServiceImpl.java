package com.jshop.service.impl;

import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;
import com.jshop.exception.InternalServerErrorException;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.jdbc.ResultSetHandler;
import com.jshop.jdbc.ResultSetHandlerFactory;
import com.jshop.service.OrderService;
import com.jshop.service.ProductService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final ResultSetHandler<List<Product>> productsResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);

    private final DataSource dataSource;

    public ProductServiceImpl(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }


    @Override
    public List<Product> listAllProducts(int page, int limit) {
        try (Connection c = dataSource.getConnection()) {
            int offset = (page - 1) * limit;
            return JDBCUtils.select(c, "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
                                       + "where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?", productsResultSetHandler, limit, offset);
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
        return null;
    }

    @Override
    public List<Category> listAllCategories() {
        return null;
    }

    @Override
    public List<Producer> listAllProducers() {
        return null;
    }
}
