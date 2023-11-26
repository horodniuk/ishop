package com.jshop.repository;

import com.jshop.entity.Product;
import com.jshop.form.SearchForm;
import com.jshop.framework.annotation.jdbc.CollectionItem;
import com.jshop.framework.annotation.jdbc.Select;
import com.jshop.repository.buider.CountProductsSearchFormSQLBuilder;
import com.jshop.repository.buider.ListProductsSearchFormSQLBuilder;

import java.util.List;

public interface ProductRepository {
    @Select("select p.*, c.name as category, pr.name as producer from product p, producer pr, category c where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?")
    @CollectionItem(Product.class)
    List<Product> listAllProducts(int limit, int offset);

    @Select("select count(*) from product")
    int countAllProducts();

    @Select("select p.*, c.name as category, pr.name as producer from product p, category c, producer pr where c.url=? and pr.id=p.id_producer and c.id=p.id_category order by p.id limit ? offset ?")
    @CollectionItem(Product.class)
    List<Product> listProductsByCategory(String categoryUrl, int limit, int offset);

    @Select("select count(p.*) from product p, category c where c.id=p.id_category and c.url=?")
    int countProductsByCategory(String categoryUrl);

    @Select(value = "", sqlBuilderClass = ListProductsSearchFormSQLBuilder.class)
    @CollectionItem(Product.class)
    List<Product> listProductsBySearchForm(SearchForm searchForm, int limit, int offset);

    @Select(value = "", sqlBuilderClass = CountProductsSearchFormSQLBuilder.class)
    int countProductsBySearchForm(SearchForm searchForm);

    @Select("select p.*, c.name as category, pr.name as producer from product p, producer pr, category c where c.id=p.id_category and pr.id=p.id_producer and p.id=?")
    Product findById(int idProduct);
}
