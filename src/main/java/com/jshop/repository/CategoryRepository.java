package com.jshop.repository;

import com.jshop.entity.Category;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface CategoryRepository extends Repository<Category, Integer> {

    List<Category> findAll(Sort sort);

}
