package com.jshop.repository;


import com.jshop.entity.Product;
import com.jshop.form.SearchForm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SearchProductRepository {

    List<Product> listProductsBySearchForm(SearchForm searchForm, Pageable pageable);

    int countProductsBySearchForm(SearchForm searchForm);
}
