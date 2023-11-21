package com.jshop.service.impl;

import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.IntResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;
import com.jshop.exception.InternalServerErrorException;
import com.jshop.form.SearchForm;

import com.jshop.jdbc.JDBCUtils;
import com.jshop.jdbc.SearchQuery;
import com.jshop.repository.CategoryRepository;
import com.jshop.repository.ProducerRepository;
import com.jshop.repository.ProductRepository;
import com.jshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Transactional
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final CategoryRepository categoryRepository;


    public ProductServiceImpl(ServiceManager serviceManager) {
        this.productRepository = serviceManager.productRepository;
        this.producerRepository = serviceManager.producerRepository;
        this.categoryRepository = serviceManager.categoryRepository;
    }

    @Override
    public List<Product> listAllProducts(int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listAllProducts(offset, limit);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listProductsByCategory(categoryUrl, offset, limit);
    }

    @Override
    public int countAllProducts() {
        return productRepository.countAllProducts();
    }

    @Override
    public List<Category> listAllCategories() {
        return categoryRepository.listAllCategories();
    }

    @Override
    public List<Producer> listAllProducers() {
        return producerRepository.listAllProducers();
    }

    @Override
    public int countProductsByCategory(String categoryUrl) {
        return productRepository.countProductsByCategory(categoryUrl);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listProductsBySearchForm(searchForm, offset, limit);
    }

    @Override
    public int countProductsBySearchForm(SearchForm form) {
        return productRepository.countProductsBySearchForm(form);
    }
}
