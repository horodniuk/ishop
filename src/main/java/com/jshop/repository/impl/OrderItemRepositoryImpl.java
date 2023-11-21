package com.jshop.repository.impl;

import com.jshop.entity.Order;
import com.jshop.entity.OrderItem;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.DefaultUniqueResultSetHandler;
import com.jshop.framework.handler.IntResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.repository.OrderItemRepository;

import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler = new DefaultListResultSetHandler<>(OrderItem.class);
    private final ResultSetHandler<OrderItem> orderItemResultSetHandler = new DefaultUniqueResultSetHandler<>(OrderItem.class);

    @Override
    public List<OrderItem> findByIdOrder(Long idOrder) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select o.id, o.id_order as id_order, o.id_product, o.count, p.id as id_product, p.name, p.description, p.price, p.image_link, "
                + "c.name as category, pr.name as producer from order_item o, product p, category c, producer pr "
                + "where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?",
                orderItemListResultSetHandler, idOrder);
    }

    @Override
    public void create(OrderItem orderItem) {
        OrderItem createdOrderItem = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(),
                "insert into order_item values(nextval('order_item_seq'),?,?,?)", orderItemResultSetHandler,
                orderItem.getIdOrder(), orderItem.getProduct().getId(), orderItem.getCount());
        orderItem.setId(createdOrderItem.getId());
    }


}
