package com.jshop.repository;

import com.jshop.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findByIdOrder(Long idOrder);

    void create(OrderItem orderItem);
}
