package com.jshop.repository;

import com.jshop.entity.Category;
import com.jshop.framework.annotation.jdbc.CollectionItem;
import com.jshop.framework.annotation.jdbc.JDBCRepository;
import com.jshop.framework.annotation.jdbc.Select;

import java.util.List;

@JDBCRepository
public interface CategoryRepository {

    @Select("select * from category order by id")
    @CollectionItem(Category.class)
    List<Category> listAllCategories();

}
