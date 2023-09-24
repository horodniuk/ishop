package com.jshop.service;

import com.jshop.entity.Category;
import com.jshop.entity.Producer;
import com.jshop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts(int page, int limit);

    List<Product> listProductsByCategory(String categoryUrl, int page, int limit);

    List<Category> listAllCategories();

    List<Producer> listAllProducers();
}
