package com.jshop.service.impl;




import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;
import com.jshop.form.SearchForm;

import com.jshop.repository.CategoryRepository;
import com.jshop.repository.ProducerRepository;
import com.jshop.repository.ProductRepository;
import com.jshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Transactional(readOnly=true)
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> listAllProducts(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return productRepository.findByCategoryUrl(categoryUrl,pageable);
    }

    @Override
    public List<Category> listAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @Override
    public List<Producer> listAllProducers() {
        return producerRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

    @Override
    public int countAllProducts() {
        return (int)productRepository.count();
    }

    @Override
    public int countProductsByCategory(String categoryUrl) {
        return productRepository.countByCategoryUrl(categoryUrl);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return productRepository.listProductsBySearchForm(searchForm, pageable);
    }

    @Override
    public int countProductsBySearchForm(SearchForm searchForm) {
        return productRepository.countProductsBySearchForm(searchForm);
    }
}
