package com.jshop.repository;

import com.jshop.entity.Order;

import java.util.List;

public interface OrderRepository {
    void create(Order order);

    Order findById(Long id);

    List<Order> listMyOrders(Integer idAccount, int offset, int limit);

    int countMyOrders(Integer idAccount);
}
