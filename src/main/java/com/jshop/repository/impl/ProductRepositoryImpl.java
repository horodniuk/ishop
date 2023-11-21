package com.jshop.repository.impl;

import com.jshop.entity.Product;
import com.jshop.form.SearchForm;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.DefaultUniqueResultSetHandler;
import com.jshop.framework.handler.IntResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.jdbc.SearchQuery;
import com.jshop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    private final ResultSetHandler<List<Product>> productsResultSetHandler = new DefaultListResultSetHandler<>(Product.class);
    private final ResultSetHandler<Product> productResultSetHandler = new DefaultUniqueResultSetHandler<>(Product.class);
    private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();


    @Override
    public List<Product> listAllProducts(int offset, int limit) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
                + "where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?",
                productsResultSetHandler, limit, offset);
    }

    @Override
    public int countAllProducts() {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select count(*) from product", countResultSetHandler);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int offset, int limit) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select p.*, c.name as category, pr.name as producer from product p, category c, producer pr "
                + "where c.url=? and pr.id=p.id_producer and c.id=p.id_category order by p.id limit ? offset ?",
                productsResultSetHandler, categoryUrl, limit, offset);
    }

    @Override
    public int countProductsByCategory(String categoryUrl) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select count(p.*) from product p, category c "
                + "where c.id=p.id_category and c.url=?",
                countResultSetHandler, categoryUrl);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, int offset, int limit) {
        SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer", searchForm);
        sq.getSql().append(" order by p.id limit ? offset ?");
        sq.getParams().add(limit);
        sq.getParams().add(offset);
        LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), sq.getSql().toString(), productsResultSetHandler, sq.getParams().toArray());
    }

    @Override
    public int countProductsBySearchForm(SearchForm searchForm) {
        SearchQuery sq = buildSearchQuery("count(*)", searchForm);
        LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), sq.getSql().toString(), countResultSetHandler, sq.getParams().toArray());
    }

    protected SearchQuery buildSearchQuery(String selectFields, SearchForm form) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select ");
        sql.append(selectFields).append(" from product p, category c, producer pr where pr.id=p.id_producer and "
                                        + "c.id=p.id_category and (p.name ilike ? or p.description ilike ?)");
        params.add("%" + form.getQuery() + "%");
        params.add("%" + form.getQuery() + "%");
        JDBCUtils.populateSqlAndParams(sql, params, form.getCategories(), "c.id = ?");
        JDBCUtils.populateSqlAndParams(sql, params, form.getProducers(), "pr.id = ?");
        return new SearchQuery(sql, params);
    }

    @Override
    public Product findById(int idProduct) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select p.*, c.name as category, pr.name as producer "
                 + "from product p, producer pr, category c "
                 + "where c.id=p.id_category and pr.id=p.id_producer and p.id=?",
                 productResultSetHandler, idProduct);
    }
}
