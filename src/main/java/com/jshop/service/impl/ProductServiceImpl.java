package com.jshop.service.impl;

import com.jshop.framework.annotation.Autowired;
import com.jshop.framework.annotation.Component;
import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;
import com.jshop.form.SearchForm;

import com.jshop.repository.CategoryRepository;
import com.jshop.repository.ProducerRepository;
import com.jshop.repository.ProductRepository;
import com.jshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Transactional
@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> listAllProducts(int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listAllProducts(limit, offset);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listProductsByCategory(categoryUrl, limit, offset);
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
    public int countAllProducts() {
        return productRepository.countAllProducts();
    }

    @Override
    public int countProductsByCategory(String categoryUrl) {
        return productRepository.countProductsByCategory(categoryUrl);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit) {
        int offset = (page - 1) * limit;
        return productRepository.listProductsBySearchForm(searchForm, limit, offset);
    }

    @Override
    public int countProductsBySearchForm(SearchForm searchForm) {
        return productRepository.countProductsBySearchForm(searchForm);
    }
}
