package com.jshop.repository;

import com.jshop.entity.Product;
import com.jshop.form.SearchForm;

import java.util.List;

public interface ProductRepository {
    List<Product> listAllProducts(int offset, int limit);

    int countAllProducts();

    List<Product> listProductsByCategory(String categoryUrl, int offset, int limit);

    int countProductsByCategory(String categoryUrl);

    List<Product> listProductsBySearchForm(SearchForm searchForm, int offset, int limit);

    int countProductsBySearchForm(SearchForm searchForm);

    Product findById(int idProduct);
}
