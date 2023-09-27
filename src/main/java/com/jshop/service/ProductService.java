package com.jshop.service;

import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;
import com.jshop.form.SearchForm;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts(int page, int limit);

    int countAllProducts();

    List<Product> listProductsByCategory(String categoryUrl, int page, int limit);

    int countProductsByCategory(String categoryUrl);

    List<Category> listAllCategories();

    List<Producer> listAllProducers();

    List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit);

    int countProductsBySearchForm(SearchForm searchForm);
}
