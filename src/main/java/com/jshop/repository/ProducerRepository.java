package com.jshop.repository;

import com.jshop.entity.Producer;
import com.jshop.framework.annotation.jdbc.CollectionItem;
import com.jshop.framework.annotation.jdbc.JDBCRepository;
import com.jshop.framework.annotation.jdbc.Select;

import java.util.List;

@JDBCRepository
public interface ProducerRepository {

     @Select("select * from producer order by name")
     @CollectionItem(Producer.class)
     List<Producer> listAllProducers();

}
