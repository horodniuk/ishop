package com.jshop.repository;

import com.jshop.entity.Order;
import com.jshop.framework.annotation.jdbc.CollectionItem;
import com.jshop.framework.annotation.jdbc.Insert;
import com.jshop.framework.annotation.jdbc.JDBCRepository;
import com.jshop.framework.annotation.jdbc.Select;

import java.util.List;

@JDBCRepository
public interface OrderRepository {

    @Insert
    void create(Order order);

    @Select("select * from \"order\" where id=?")
    Order findById(Long id);

    @Select("select * from \"order\" where id_account=? order by id desc limit ? offset ?")
    @CollectionItem(Order.class)
    List<Order> listMyOrders(Integer idAccount, int offset, int limit);

    @Select("select count(*) from \"order\" where id_account=?")
    int countMyOrders(Integer idAccount);
}
